package com.softeen.uishowcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softeen.uishowcase.ui.hub.HubScreen
import com.softeen.uishowcase.ui.drape.DrapeScreen
import com.softeen.uishowcase.ui.medicare.MediCareScreen
import com.softeen.uishowcase.ui.tastique.TastiqueScreen
import com.softeen.uishowcase.ui.nexo.NexoScreen
import com.softeen.uishowcase.ui.pericia.PericiaScreen
import com.softeen.uishowcase.ui.cerka.CerkaScreen

object AppRoutes {
    const val HUB = "hub"
    const val TASTIQUE = "tastique"
    const val MEDICARE = "medicare"
    const val DRAPE = "drape"
    const val NEXO = "nexo"
    const val PERICIA = "pericia"
    const val CERKA = "cerka"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HUB) {
        composable(AppRoutes.HUB)      { HubScreen(navController) }
        composable(AppRoutes.TASTIQUE) { TastiqueScreen(navController) }
        composable(AppRoutes.MEDICARE) { MediCareScreen(navController) }
        composable(AppRoutes.DRAPE)    { DrapeScreen(navController) }
        composable(AppRoutes.NEXO)    { NexoScreen(navController) }
        composable(AppRoutes.PERICIA) { PericiaScreen(navController) }
        composable(AppRoutes.CERKA)   { CerkaScreen(navController) }
    }
}

