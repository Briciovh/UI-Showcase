package com.softeen.uishowcase.ui.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softeen.uishowcase.navigation.AppRoutes
import com.softeen.uishowcase.ui.theme.CerkaViolet
import com.softeen.uishowcase.ui.theme.ChispaRose
import com.softeen.uishowcase.ui.theme.DrapeCoral
import com.softeen.uishowcase.ui.theme.MediCareTeal
import com.softeen.uishowcase.ui.theme.NexoBlue
import com.softeen.uishowcase.ui.theme.PericiaNavy
import com.softeen.uishowcase.ui.theme.TastiqueAmber
import com.softeen.uishowcase.ui.theme.VortexCyan

@Composable
fun HubScreen(navController: NavController) {
    val cards = listOf(
        Triple("Tastique",  "Food Delivery",               TastiqueAmber to (Icons.Default.ShoppingCart to AppRoutes.TASTIQUE)),
        Triple("MediCare",  "Healthcare",                   MediCareTeal  to (Icons.Default.Favorite     to AppRoutes.MEDICARE)),
        Triple("Drape",     "Fashion Retail",               DrapeCoral    to (Icons.Default.Star          to AppRoutes.DRAPE)),
        Triple("Nexo",      "Service Marketplace",          NexoBlue      to (Icons.Default.Build         to AppRoutes.NEXO)),
        Triple("Pericia",   "Seguimiento de Siniestros",    PericiaNavy   to (Icons.Default.Info          to AppRoutes.PERICIA)),
        Triple("Cerka",     "Marketplace de Servicios",     CerkaViolet   to (Icons.Default.Person        to AppRoutes.CERKA)),
        Triple("Chispa",    "App de Citas",                 ChispaRose    to (Icons.Default.Favorite      to AppRoutes.CHISPA)),
        Triple("Vórtex",    "Instant Messaging",            VortexCyan    to (Icons.AutoMirrored.Filled.Send to AppRoutes.VORTEX_LIST)),
    )
    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp, end = 24.dp,
                top = innerPadding.calculateTopPadding() + 24.dp,
                bottom = innerPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("UI Showcase", style = MaterialTheme.typography.headlineMedium)
                Text("Select a demo", style = MaterialTheme.typography.bodyMedium)
            }
            items(cards) { (title, industry, rest) ->
                val (accent, iconRoute) = rest
                val (icon, route) = iconRoute
                ShowcaseCard(
                    title = title,
                    industry = industry,
                    accentColor = accent,
                    icon = icon,
                    route = route,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun ShowcaseCard(
    title: String,
    industry: String,
    accentColor: Color,
    icon: ImageVector,
    route: String,
    navController: NavController
) {
    Card(
        onClick = { navController.navigate(route) },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.18f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(accentColor.copy(alpha = 0.30f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
                Text(industry, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
