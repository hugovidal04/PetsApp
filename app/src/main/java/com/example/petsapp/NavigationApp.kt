package com.example.petsapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petsapp.presentation.physicalActivity.PhysicalActivityScreen
import com.example.petsapp.presentation.calendar.CalendarScreen
import com.example.petsapp.presentation.admin.AdminScreen
import com.example.petsapp.presentation.diary.DiaryScreen
import com.example.petsapp.presentation.feeding.FeedingScreen
import com.example.petsapp.presentation.initial.InitialScreen
import com.example.petsapp.presentation.login.LoginScreen
import com.example.petsapp.presentation.map.MapScreen
import com.example.petsapp.presentation.pets.PetsScreen
import com.example.petsapp.presentation.settings.SettingsScreen
import com.example.petsapp.presentation.signup.SingupScreen

@Composable
fun NavigationApp(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("login") }
            )
        }
        composable("login") {
            LoginScreen(
                navigateToHome = { navHostController.navigate("pets") },
                navigateToAdmin = { navHostController.navigate("admin") },
                navigateToSignup = { navHostController.navigate("signup") }
            )
        }
        composable("signup") {
            SingupScreen(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToHome = { navHostController.navigate("pets") }
            )
        }
        composable("admin") {
            AdminScreen(
                onLogout = { navHostController.navigate("initial") }
            )
        }
        composable("pets") {
            PetsScreen(navHostController)
        }
        composable("calendar") {
            CalendarScreen(navController = navHostController)
        }
        composable("feeding") {
            FeedingScreen(navController = navHostController)
        }
        composable("physical_activity") {
            PhysicalActivityScreen(navController = navHostController)
        }
        composable("diary") {
            DiaryScreen(navController = navHostController)
        }
        composable("map") {
            MapScreen(navController = navHostController)
        }
        composable("settings") {
            SettingsScreen(navController = navHostController)
        }
    }
}