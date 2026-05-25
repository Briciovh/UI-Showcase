package com.softeen.uishowcase.ui.hub

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import com.softeen.uishowcase.R
import com.softeen.uishowcase.navigation.AppRoutes
import com.softeen.uishowcase.ui.theme.CerkaViolet
import com.softeen.uishowcase.ui.theme.ChispaRose
import com.softeen.uishowcase.ui.theme.DrapeCoral
import com.softeen.uishowcase.ui.theme.MediCareTeal
import com.softeen.uishowcase.ui.theme.NexoBlue
import com.softeen.uishowcase.ui.theme.PericiaNavy
import com.softeen.uishowcase.ui.theme.TastiqueAmber
import com.softeen.uishowcase.ui.theme.VortexCyan
import com.softeen.uishowcase.ui.theme.VigiaBlue
import com.softeen.uishowcase.ui.theme.PortonBlue

@Composable
fun HubScreen(navController: NavController) {
    val cards = listOf(
        Triple(stringResource(R.string.tastique_title), stringResource(R.string.industry_food_delivery),       TastiqueAmber to (Icons.Default.ShoppingCart       to AppRoutes.TASTIQUE)),
        Triple(stringResource(R.string.medicare_title), stringResource(R.string.industry_healthcare),         MediCareTeal  to (Icons.Default.Favorite           to AppRoutes.MEDICARE)),
        Triple(stringResource(R.string.drape_title),    stringResource(R.string.industry_fashion_retail),     DrapeCoral    to (Icons.Default.Star               to AppRoutes.DRAPE)),
        Triple(stringResource(R.string.nexus_title),    stringResource(R.string.industry_service_marketplace),NexoBlue      to (Icons.Default.Build              to AppRoutes.NEXUS)),
        Triple(stringResource(R.string.expert_title),   stringResource(R.string.industry_claims_tracking),    PericiaNavy   to (Icons.Default.Info               to AppRoutes.EXPERT)),
        Triple(stringResource(R.string.near_title),     stringResource(R.string.industry_service_marketplace),CerkaViolet   to (Icons.Default.Person             to AppRoutes.NEAR)),
        Triple(stringResource(R.string.spark_title),    stringResource(R.string.industry_dating_app),         ChispaRose    to (Icons.Default.Favorite           to AppRoutes.SPARK)),
        Triple(stringResource(R.string.vortex_title),   stringResource(R.string.industry_messaging),          VortexCyan    to (Icons.AutoMirrored.Filled.Send   to AppRoutes.VORTEX_LIST)),
        Triple(stringResource(R.string.sentry_title),   stringResource(R.string.industry_child_safety),       VigiaBlue     to (Icons.Default.LocationOn         to AppRoutes.SENTRY)),
        Triple(stringResource(R.string.portal_title),   stringResource(R.string.industry_neighborhood_access),PortonBlue    to (Icons.Default.Lock               to AppRoutes.PORTAL)),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(stringResource(R.string.hub_title), style = MaterialTheme.typography.headlineMedium)
                        Text(stringResource(R.string.hub_subtitle), style = MaterialTheme.typography.bodyMedium)
                    }
                    LanguageSelector()
                }
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
fun LanguageSelector() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        LanguageButton("EN", "en")
        LanguageButton("ES", "es")
        LanguageButton("PT", "pt")
    }
}

@Composable
fun LanguageButton(label: String, tag: String) {
    val currentLocale = AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"
    val isSelected = currentLocale == tag
    
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(tag)
                AppCompatDelegate.setApplicationLocales(appLocale)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
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
