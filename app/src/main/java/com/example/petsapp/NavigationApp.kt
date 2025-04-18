package com.example.petsapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationApp(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            //Todo llamar a la ventana
        }
    }
}