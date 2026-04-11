package com.example.uishowcase.ui.tastique

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Private palette
private val TastiqueBg      = Color(0xFF0D0D0D)
private val TastiqueCard    = Color(0xFF222222)
private val TastiqueAmber   = Color(0xFFFFB300)
private val TastiqueOnBg    = Color(0xFFF5F5F5)
private val TastiqueSubtext = Color(0xFF9E9E9E)

@Composable
private fun TastiqueTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = TastiqueAmber,
            background = TastiqueBg,
            surface = TastiqueCard,
            onBackground = TastiqueOnBg,
            onSurface = TastiqueOnBg,
            onPrimary = Color.Black,
        ),
        content = content
    )
}

// Fake data
private val categories = listOf("All", "Popular", "Pasta", "Pizza", "Burgers", "Sushi", "Desserts")

private data class MenuItem(
    val name: String,
    val price: String,
    val rating: Float,
    val placeholderColor: Color
)

private val menuItems = listOf(
    MenuItem("Truffle Risotto",      "$28", 4.9f, Color(0xFF5D4037)),
    MenuItem("Lobster Linguine",     "$42", 4.8f, Color(0xFF4E342E)),
    MenuItem("Wagyu Burger",         "$36", 4.7f, Color(0xFF6D4C41)),
    MenuItem("Black Squid Pasta",    "$32", 4.6f, Color(0xFF37474F)),
    MenuItem("Chocolate Lava Cake",  "$18", 4.9f, Color(0xFF4A148C)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TastiqueScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf(0) }

    TastiqueTheme {
        Scaffold(
            containerColor = TastiqueBg,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = TastiqueOnBg
                            )
                        }
                    },
                    title = {
                        Text("Mayfair District", color = TastiqueOnBg)
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = TastiqueAmber
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = TastiqueBg)
                )
            },
            floatingActionButton = {
                BadgedBox(badge = { Badge { Text("3") } }) {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = TastiqueAmber,
                        contentColor = Color.Black
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                // Hero card
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF1A1A1A), Color(0xFF3D2B00))
                                )
                            )
                    ) {
                        // Amber badge top-right
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .background(TastiqueAmber, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "Chef's Special",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        // Bottom gradient overlay with dish name
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xCC000000))
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                "Truffle Wagyu Steak",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TastiqueOnBg
                            )
                        }
                    }
                }

                // Category row
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        itemsIndexed(categories) { index, category ->
                            FilterChip(
                                selected = selectedCategory == index,
                                onClick = { selectedCategory = index },
                                label = { Text(category) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TastiqueAmber,
                                    selectedLabelColor = Color.Black,
                                    containerColor = TastiqueCard,
                                    labelColor = TastiqueSubtext
                                )
                            )
                        }
                    }
                }

                // Menu items
                items(menuItems) { item ->
                    MenuItemCard(item)
                }

                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun MenuItemCard(item: MenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(TastiqueCard, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(item.placeholderColor)
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium, color = TastiqueOnBg)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = TastiqueAmber,
                    modifier = Modifier.size(14.dp)
                )
                Text(item.rating.toString(), style = MaterialTheme.typography.bodySmall, color = TastiqueSubtext)
            }
        }
        Text(item.price, style = MaterialTheme.typography.titleMedium, color = TastiqueAmber)
    }
}
