package com.example.petsapp.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    navController: NavHostController,
    viewModel: MapViewModel = viewModel()
) {
    val context = LocalContext.current
    val places by viewModel.places.collectAsState()

    var currentLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var selectedQuery by remember { mutableStateOf("veterinaria") }

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            getLocation(context) { loc ->
                currentLocation = GeoPoint(loc.latitude, loc.longitude)
                viewModel.searchPlaces(selectedQuery, loc.latitude, loc.longitude)
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    fun performSearch(query: String) {
        selectedQuery = query
        currentLocation?.let {
            viewModel.searchPlaces(query, it.latitude, it.longitude)
        }
    }

    AppDrawer(navController = navController, currentRoute = "map") {
        Box(modifier = Modifier.fillMaxSize()) {
            currentLocation?.let { location ->
                AndroidView(
                    factory = {
                        Configuration.getInstance()
                            .load(it, it.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
                        MapView(it).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            controller.setZoom(15.0)
                            controller.setCenter(location)
                            setMultiTouchControls(true)
                        }
                    },
                    update = { map ->
                        map.controller.setCenter(location)
                        map.overlays.clear()

                        //Mi ubicación
                        val userMarker = Marker(map).apply {
                            position = location
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Tu ubicación"
                            icon = ContextCompat.getDrawable(context, org.osmdroid.library.R.drawable.person)
                        }
                        map.overlays.add(userMarker)

                        //Marcadores de lugares
                        places.forEach { place ->
                            val marker = Marker(map).apply {
                                position = GeoPoint(place.lat, place.lon)
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                title = when (selectedQuery) {
                                    "veterinaria" ->  "Clínica"
                                    "tienda de animales" -> "Tienda"
                                    else -> ""
                                }
                            }
                            map.overlays.add(marker)
                        }
                        map.invalidate()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(FondoPrincipal)
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Mapa de servicios cercanos", color = ColorTexto)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { performSearch("veterinaria") }) {
                        Text("Clínicas")
                    }
                    Button(onClick = { performSearch("tienda de animales") }) {
                        Text("Tiendas")
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLocation(context: Context, onLocation: (Location) -> Unit) {
    val fused = LocationServices.getFusedLocationProviderClient(context)
    fused.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocation(location)
        }
    }
}