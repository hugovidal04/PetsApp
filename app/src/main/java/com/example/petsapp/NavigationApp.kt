package com.example.petsapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petsapp.presentation.home.HomeScreen
import com.example.petsapp.presentation.initial.InitialScreen
import com.example.petsapp.presentation.login.LoginScreen

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
                navigateToHome = { navHostController.navigate("home") } // TODO Cambiar por home
            )
        }
        composable("home") {
            HomeScreen()
        }
    }
}