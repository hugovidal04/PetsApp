package com.example.petsapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsapp.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MapViewModel : ViewModel() {

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    fun searchPlaces(queryType: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val placesFound = withContext(Dispatchers.IO) {
                    val radius = 10000 //No poner mucho
                    val tag = when (queryType) {
                        "veterinaria" -> "amenity=veterinary"
                        "tienda de animales" -> "shop=pet"
                        else -> return@withContext emptyList()
                    }

                    val query = """
                    [out:json];
                    (
                      node[$tag](around:$radius,$lat,$lon);
                      way[$tag](around:$radius,$lat,$lon);
                      relation[$tag](around:$radius,$lat,$lon);
                    );
                    out center;
                """.trimIndent()

                    val url = URL("https://overpass-api.de/api/interpreter")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    conn.setRequestProperty("User-Agent", "PetsApp/1.0")
                    conn.doOutput = true
                    conn.outputStream.write(
                        "data=${
                            URLEncoder.encode(
                                query,
                                "UTF-8"
                            )
                        }".toByteArray()
                    )

                    val response = conn.inputStream.bufferedReader().use { it.readText() }
                    val data = org.json.JSONObject(response).getJSONArray("elements")

                    List(data.length()) { i ->
                        val obj = data.getJSONObject(i)
                        val lat =
                            if (obj.has("lat")) obj.getDouble("lat") else obj.getJSONObject("center")
                                .getDouble("lat")
                        val lon =
                            if (obj.has("lon")) obj.getDouble("lon") else obj.getJSONObject("center")
                                .getDouble("lon")
                        val name = obj.optJSONObject("tags")?.optString("name") ?: "(Sin nombre)"
                        Place(name = name, lat = lat, lon = lon)
                    }
                }

                _places.value = placesFound
            } catch (e: Exception) {
                e.printStackTrace()
                _places.value = emptyList()
            }
        }
    }
}