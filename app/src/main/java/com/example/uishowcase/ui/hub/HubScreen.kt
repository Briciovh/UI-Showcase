package com.example.uishowcase.ui.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.uishowcase.navigation.AppRoutes
import com.example.uishowcase.ui.theme.DrapeCoral
import com.example.uishowcase.ui.theme.MediCareTeal
import com.example.uishowcase.ui.theme.TastiqueAmber

@Composable
fun HubScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("UI Showcase", style = MaterialTheme.typography.headlineMedium)
            Text("Select a demo", style = MaterialTheme.typography.bodyMedium)

            ShowcaseCard(
                title = "Tastique",
                industry = "Food Delivery",
                accentColor = TastiqueAmber,
                icon = Icons.Default.ShoppingCart,
                route = AppRoutes.TASTIQUE,
                navController = navController
            )
            ShowcaseCard(
                title = "MediCare",
                industry = "Healthcare",
                accentColor = MediCareTeal,
                icon = Icons.Default.Favorite,
                route = AppRoutes.MEDICARE,
                navController = navController
            )
            ShowcaseCard(
                title = "Drape",
                industry = "Fashion Retail",
                accentColor = DrapeCoral,
                icon = Icons.Default.Star,
                route = AppRoutes.DRAPE,
                navController = navController
            )
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
