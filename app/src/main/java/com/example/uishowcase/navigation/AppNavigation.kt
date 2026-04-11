package com.example.uishowcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uishowcase.ui.hub.HubScreen
import com.example.uishowcase.ui.drape.DrapeScreen
import com.example.uishowcase.ui.medicare.MediCareScreen
import com.example.uishowcase.ui.tastique.TastiqueScreen

object AppRoutes {
    const val HUB = "hub"
    const val TASTIQUE = "tastique"
    const val MEDICARE = "medicare"
    const val DRAPE = "drape"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HUB) {
        composable(AppRoutes.HUB)      { HubScreen(navController) }
        composable(AppRoutes.TASTIQUE) { TastiqueScreen(navController) }
        composable(AppRoutes.MEDICARE) { MediCareScreen(navController) }
        composable(AppRoutes.DRAPE)    { DrapeScreen(navController) }
    }
}

