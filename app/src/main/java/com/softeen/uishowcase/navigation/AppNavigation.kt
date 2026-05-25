package com.softeen.uishowcase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softeen.uishowcase.ui.hub.HubScreen
import com.softeen.uishowcase.ui.drape.DrapeScreen
import com.softeen.uishowcase.ui.medicare.MediCareScreen
import com.softeen.uishowcase.ui.tastique.TastiqueScreen
import com.softeen.uishowcase.ui.nexus.NexusScreen
import com.softeen.uishowcase.ui.expert.ExpertScreen
import com.softeen.uishowcase.ui.near.NearScreen
import com.softeen.uishowcase.ui.spark.SparkScreen
import com.softeen.uishowcase.ui.vortex.VortexListScreen
import com.softeen.uishowcase.ui.vortex.VortexChatScreen
import com.softeen.uishowcase.ui.sentry.SentryScreen
import com.softeen.uishowcase.ui.portal.PortalHomeScreen

object AppRoutes {
    const val HUB = "hub"
    const val TASTIQUE = "tastique"
    const val MEDICARE = "medicare"
    const val DRAPE = "drape"
    const val NEXUS = "nexus"
    const val EXPERT = "expert"
    const val NEAR = "near"
    const val SPARK = "spark"
    const val VORTEX_LIST = "vortex_list"
    const val VORTEX_CHAT = "vortex_chat"
    const val SENTRY = "sentry"
    const val PORTAL = "portal"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoutes.HUB) {
        composable(AppRoutes.HUB)        { HubScreen(navController) }
        composable(AppRoutes.TASTIQUE)   { TastiqueScreen(navController) }
        composable(AppRoutes.MEDICARE)   { MediCareScreen(navController) }
        composable(AppRoutes.DRAPE)      { DrapeScreen(navController) }
        composable(AppRoutes.NEXUS)      { NexusScreen(navController) }
        composable(AppRoutes.EXPERT)     { ExpertScreen(navController) }
        composable(AppRoutes.NEAR)       { NearScreen(navController) }
        composable(AppRoutes.SPARK)      { SparkScreen(navController) }
        composable(AppRoutes.VORTEX_LIST){ VortexListScreen(navController) }
        composable(AppRoutes.VORTEX_CHAT){ VortexChatScreen(navController) }
        composable(AppRoutes.SENTRY)     { SentryScreen(navController) }
        composable(AppRoutes.PORTAL)     { PortalHomeScreen(navController) }
    }
}
