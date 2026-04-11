package com.example.uishowcase.ui.drape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// Private palette
private val DrapeBg           = Color(0xFFFAF8F5)
private val DrapeCoralPrimary = Color(0xFFFF3D2E)
private val DrapeOnBg         = Color(0xFF1C1C1E)
private val DrapeSubtext      = Color(0xFF8E8E93)

@Composable
private fun DrapeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = DrapeCoralPrimary,
            background = DrapeBg,
            surface = Color.White,
            onBackground = DrapeOnBg,
            onSurface = DrapeOnBg,
            onPrimary = Color.White,
        ),
        content = content
    )
}

// Fake data
private val filters = listOf("All", "New In", "Dresses", "Tops", "Trousers", "Accessories")

private data class Product(
    val name: String,
    val price: String,
    val rating: Float,
    val placeholderColor: Color,
    val isFavorite: Boolean
)

private val products = listOf(
    Product("Linen Midi Dress",    "$189", 4.8f, Color(0xFFD7CCC8), isFavorite = true),
    Product("Silk Wrap Top",       "$124", 4.7f, Color(0xFFB0BEC5), isFavorite = false),
    Product("Wide Leg Trousers",   "$156", 4.6f, Color(0xFFCFD8DC), isFavorite = false),
    Product("Cashmere Knit",       "$210", 4.9f, Color(0xFFBCAAA4), isFavorite = true),
    Product("Leather Belt Bag",    "$98",  4.5f, Color(0xFFA1887F), isFavorite = false),
    Product("Tailored Blazer",     "$275", 4.8f, Color(0xFF90A4AE), isFavorite = false),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrapeScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf(0) }
    val favoriteStates = remember { mutableStateListOf(*products.map { it.isFavorite }.toTypedArray()) }

    DrapeTheme {
        Scaffold(
            containerColor = DrapeBg,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DrapeOnBg
                            )
                        }
                    },
                    title = {
                        Text(
                            "drape",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            ),
                            color = DrapeOnBg
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = DrapeOnBg)
                        }
                        IconButton(onClick = {}) {
                            BadgedBox(badge = { Badge { Text("2") } }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = DrapeOnBg)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DrapeBg)
                )
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {

                // Search
                item {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Search styles, brands...", color = DrapeSubtext) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = DrapeSubtext)
                        },
                        shape = RoundedCornerShape(28.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DrapeCoralPrimary,
                            unfocusedBorderColor = DrapeCoralPrimary.copy(alpha = 0.5f),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Filters
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        itemsIndexed(filters) { index, filter ->
                            FilterChip(
                                selected = selectedFilter == index,
                                onClick = { selectedFilter = index },
                                label = { Text(filter) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color.Black,
                                    selectedLabelColor = Color.White,
                                    containerColor = DrapeBg,
                                    labelColor = DrapeOnBg
                                )
                            )
                        }
                    }
                }

                // Product grid (non-lazy, avoids nested scroll)
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        products
                            .mapIndexed { i, p -> Pair(i, p) }
                            .chunked(2)
                            .forEach { rowItems ->
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    rowItems.forEach { (index, product) ->
                                        ProductCard(
                                            product = product,
                                            isFavorite = favoriteStates[index],
                                            onFavoriteToggle = { favoriteStates[index] = !favoriteStates[index] },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    if (rowItems.size < 2) {
                                        Spacer(Modifier.weight(1f))
                                    }
                                }
                            }
                    }
                }

                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(product.placeholderColor)
        ) {
            IconButton(
                onClick = onFavoriteToggle,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favourite",
                    tint = if (isFavorite) DrapeCoralPrimary else Color.White
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            product.name,
            style = MaterialTheme.typography.bodyMedium,
            color = DrapeOnBg,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(product.price, style = MaterialTheme.typography.titleSmall, color = DrapeCoralPrimary)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = DrapeCoralPrimary,
                modifier = Modifier.size(12.dp)
            )
            Text(product.rating.toString(), style = MaterialTheme.typography.labelSmall, color = DrapeSubtext)
        }
    }
}
