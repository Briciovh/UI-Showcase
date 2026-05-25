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
import com.softeen.uishowcase.ui.chispa.ChispaScreen
import com.softeen.uishowcase.ui.vortex.VortexListScreen
import com.softeen.uishowcase.ui.vortex.VortexChatScreen
import com.softeen.uishowcase.ui.vigia.VigiaScreen
import com.softeen.uishowcase.ui.porton.PortonHomeScreen

object AppRoutes {
    const val HUB = "hub"
    const val TASTIQUE = "tastique"
    const val MEDICARE = "medicare"
    const val DRAPE = "drape"
    const val NEXO = "nexo"
    const val PERICIA = "pericia"
    const val CERKA = "cerka"
    const val CHISPA = "chispa"
    const val VORTEX_LIST = "vortex_list"
    const val VORTEX_CHAT = "vortex_chat"
    const val VIGIA = "vigia"
    const val PORTON = "porton"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HUB) {
        composable(AppRoutes.HUB)        { HubScreen(navController) }
        composable(AppRoutes.TASTIQUE)   { TastiqueScreen(navController) }
        composable(AppRoutes.MEDICARE)   { MediCareScreen(navController) }
        composable(AppRoutes.DRAPE)      { DrapeScreen(navController) }
        composable(AppRoutes.NEXO)       { NexoScreen(navController) }
        composable(AppRoutes.PERICIA)    { PericiaScreen(navController) }
        composable(AppRoutes.CERKA)      { CerkaScreen(navController) }
        composable(AppRoutes.CHISPA)     { ChispaScreen(navController) }
        composable(AppRoutes.VORTEX_LIST){ VortexListScreen(navController) }
        composable(AppRoutes.VORTEX_CHAT){ VortexChatScreen(navController) }
        composable(AppRoutes.VIGIA)      { VigiaScreen(navController) }
        composable(AppRoutes.PORTON)     { PortonHomeScreen(navController) }
    }
}

