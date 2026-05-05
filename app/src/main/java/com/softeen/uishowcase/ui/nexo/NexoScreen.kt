package com.softeen.uishowcase.ui.nexo

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Private palette
private val NexoBg      = Color(0xFFF5F7FF)
private val NexoPrimary = Color(0xFF1B4FD8)
private val NexoAccent  = Color(0xFFFF6B2C)
private val NexoOnBg    = Color(0xFF0F172A)
private val NexoSubtext = Color(0xFF64748B)

@Composable
private fun NexoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = NexoPrimary,
            secondary = NexoAccent,
            background = NexoBg,
            surface = Color.White,
            onBackground = NexoOnBg,
            onSurface = NexoOnBg,
            onPrimary = Color.White,
        ),
        content = content
    )
}

// --- Datos ficticios ---

private data class Categoria(val nombre: String, val icono: ImageVector)
private data class Proveedor(
    val nombre: String,
    val especialidad: String,
    val calificacion: Float,
    val distancia: String,
    val colorAvatar: Color
)
private data class Servicio(
    val titulo: String,
    val proveedor: String,
    val precio: String,
    val calificacion: Float,
    val etiqueta: String,
    val colorFondo: Color,
    val icono: ImageVector
)

private val categorias = listOf(
    Categoria("Hogar",         Icons.Default.Home),
    Categoria("Turismo",       Icons.Default.LocationOn),
    Categoria("Mantenimiento", Icons.Default.Build),
    Categoria("Reparación",    Icons.Default.Settings),
    Categoria("Salud",         Icons.Default.Favorite),
    Categoria("Educación",     Icons.Default.Star),
)

private val proveedores = listOf(
    Proveedor("Carlos Mendoza", "Electricista",  4.9f, "1.2 km", Color(0xFF3D5AFE)),
    Proveedor("María González", "Plomería",      4.7f, "2.1 km", Color(0xFF00BCD4)),
    Proveedor("José Ramírez",   "Pintura",       4.8f, "0.8 km", Color(0xFFFF6B2C)),
    Proveedor("Ana Flores",     "Turismo",       4.6f, "3.5 km", Color(0xFF4CAF50)),
    Proveedor("Luis Vega",      "A/C y Clima",   4.5f, "4.0 km", Color(0xFF9C27B0)),
)

private val servicios = listOf(
    Servicio(
        "Instalación eléctrica completa", "Carlos Mendoza",
        "$500 – $1,200", 4.9f, "Más popular", Color(0xFF1B4FD8), Icons.Default.Build
    ),
    Servicio(
        "Tour por la ciudad histórica", "Ana Flores",
        "$200 – $500", 4.6f, "Nuevo", Color(0xFF00897B), Icons.Default.LocationOn
    ),
    Servicio(
        "Mantenimiento de aire acondicionado", "Luis Vega",
        "$350 – $800", 4.8f, "Más popular", Color(0xFF6C3FE0), Icons.Default.Settings
    ),
    Servicio(
        "Plomería de emergencia 24h", "María González",
        "$400 – $900", 4.7f, "", Color(0xFF0277BD), Icons.Default.Home
    ),
)

// --- Pantalla principal ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NexoScreen(navController: NavController) {
    var catSeleccionada by remember { mutableStateOf(0) }

    NexoTheme {
        Scaffold(
            containerColor = NexoBg,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = NexoOnBg
                            )
                        }
                    },
                    title = {
                        Text(
                            "nexo",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = NexoPrimary
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = NexoOnBg)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Person, contentDescription = "Perfil", tint = NexoPrimary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = NexoBg)
                )
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item { UbicacionYBusqueda() }
                item { BannerHero() }
                item { SeccionCategorias(catSeleccionada) { catSeleccionada = it } }
                item { SeccionProveedores() }
                item { SeccionServicios() }
                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun UbicacionYBusqueda() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = NexoAccent, modifier = Modifier.size(16.dp))
            Text(
                "Ciudad de México",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = NexoOnBg
            )
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = NexoSubtext, modifier = Modifier.size(16.dp))
        }

        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = true,
            placeholder = { Text("¿Qué servicio necesitas?", color = NexoSubtext) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = NexoSubtext) },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NexoPrimary,
                unfocusedBorderColor = Color(0xFFDDE3FF),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            )
        )
    }
}

@Composable
private fun BannerHero() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(185.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF1B4FD8), Color(0xFF6366F1))))
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                "¡Bienvenido!",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                "Servicios profesionales\na tu alcance",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                "+500 proveedores en tu ciudad",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(6.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = NexoAccent),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "Explorar ahora",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun SeccionCategorias(seleccionada: Int, onSeleccionar: (Int) -> Unit) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Text(
            "Categorías",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = NexoOnBg,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(categorias) { i, cat ->
                FilterChip(
                    selected = seleccionada == i,
                    onClick = { onSeleccionar(i) },
                    label = { Text(cat.nombre) },
                    leadingIcon = {
                        Icon(cat.icono, contentDescription = null, modifier = Modifier.size(16.dp))
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NexoPrimary,
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White,
                    )
                )
            }
        }
    }
}

@Composable
private fun SeccionProveedores() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Proveedores destacados",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = NexoOnBg
            )
            TextButton(onClick = {}) {
                Text("Ver todos", style = MaterialTheme.typography.labelMedium, color = NexoPrimary)
            }
        }
        Spacer(Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(proveedores) { _, prov ->
                TarjetaProveedor(prov)
            }
        }
    }
}

@Composable
private fun TarjetaProveedor(proveedor: Proveedor) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(proveedor.colorAvatar),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    proveedor.nombre.split(" ").take(2).joinToString("") { it.first().toString() },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
            Text(
                proveedor.nombre,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = NexoOnBg
            )
            Text(proveedor.especialidad, style = MaterialTheme.typography.bodySmall, color = NexoSubtext)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                Text(proveedor.calificacion.toString(), style = MaterialTheme.typography.labelSmall, color = NexoOnBg)
            }
            MiniChip(proveedor.distancia, NexoPrimary.copy(alpha = 0.1f), NexoPrimary)
        }
    }
}

@Composable
private fun SeccionServicios() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Servicios populares",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = NexoOnBg
            )
            TextButton(onClick = {}) {
                Text("Ver todos", style = MaterialTheme.typography.labelMedium, color = NexoPrimary)
            }
        }
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            servicios.forEach { servicio -> TarjetaServicio(servicio) }
        }
    }
}

@Composable
private fun TarjetaServicio(servicio: Servicio) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(servicio.colorFondo),
                contentAlignment = Alignment.Center
            ) {
                Icon(servicio.icono, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        servicio.titulo,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = NexoOnBg,
                        modifier = Modifier.weight(1f)
                    )
                    if (servicio.etiqueta.isNotEmpty()) {
                        Spacer(Modifier.width(6.dp))
                        EtiquetaBadge(servicio.etiqueta)
                    }
                }
                Text(servicio.proveedor, style = MaterialTheme.typography.bodySmall, color = NexoSubtext)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        servicio.precio,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = NexoPrimary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(13.dp))
                        Text(servicio.calificacion.toString(), style = MaterialTheme.typography.labelSmall, color = NexoSubtext)
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniChip(texto: String, fondo: Color, colorTexto: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(fondo)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(texto, style = MaterialTheme.typography.labelSmall, color = colorTexto)
    }
}

@Composable
private fun EtiquetaBadge(etiqueta: String) {
    val fondo: Color
    val colorTexto: Color
    when (etiqueta) {
        "Más popular" -> {
            fondo = NexoAccent.copy(alpha = 0.15f)
            colorTexto = NexoAccent
        }
        "Nuevo" -> {
            fondo = Color(0xFF4CAF50).copy(alpha = 0.15f)
            colorTexto = Color(0xFF2E7D32)
        }
        else -> {
            fondo = NexoPrimary.copy(alpha = 0.1f)
            colorTexto = NexoPrimary
        }
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(fondo)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            etiqueta,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = colorTexto
        )
    }
}
