package com.example.petsapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petsapp.presentation.admin.AdminScreen
import com.example.petsapp.presentation.home.HomeScreen
import com.example.petsapp.presentation.initial.InitialScreen
import com.example.petsapp.presentation.login.LoginScreen
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
                navigateToHome = { navHostController.navigate("home") },
                navigateToAdmin = { navHostController.navigate("admin") },
                navigateToSignup = { navHostController.navigate("signup") }
            )
        }
        composable("signup") {
            SingupScreen()
        }
        composable("home") {
            HomeScreen()
        }
        composable("admin") {
            AdminScreen(
                onLogout = { navHostController.navigate("initial") }
            )
        }
    }
}