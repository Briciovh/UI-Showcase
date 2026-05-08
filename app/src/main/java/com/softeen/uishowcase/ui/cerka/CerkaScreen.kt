package com.softeen.uishowcase.ui.cerka

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Private palette
private val CerkaFondo    = Color(0xFFF8F9FA)
private val CerkaPrimario = Color(0xFF311B92)
private val CerkaAcento   = Color(0xFF00C853)
private val CerkaAmbar    = Color(0xFFFFB300)
private val CerkaRojo     = Color(0xFFD50000)
private val CerkaSurface  = Color.White
private val CerkaSubtexto = Color(0xFF757575)
private val CerkaOnFondo  = Color(0xFF1A1A1A)

@Composable
private fun CerkaTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary      = CerkaPrimario,
            secondary    = CerkaAcento,
            background   = CerkaFondo,
            surface      = CerkaSurface,
            onBackground = CerkaOnFondo,
            onSurface    = CerkaOnFondo,
            onPrimary    = Color.White,
        ),
        content = content
    )
}

// --- Data ---

private data class Proveedor(
    val nombre: String,
    val especialidad: String,
    val rating: Float,
    val distancia: String,
    val eta: String,
    val precio: String,
    val iniciales: String,
    val colorAvatar: Color
)

private data class PedidoActivo(
    val proveedor: String,
    val especialidad: String,
    val eta: String,
    val progreso: Float,
    val iniciales: String,
    val estado: String
)

private data class PedidoReciente(
    val servicio: String,
    val proveedor: String,
    val fecha: String,
    val monto: String,
    val estado: String,
    val colorEstado: Color,
    val icono: ImageVector
)

private data class Categoria(val nombre: String, val icono: ImageVector)

private val pedidoActivo = PedidoActivo(
    proveedor    = "Miguel Ángel Ramos",
    especialidad = "Mensajería express",
    eta          = "8 min",
    progreso     = 0.60f,
    iniciales    = "MR",
    estado       = "En camino"
)

private val proveedores = listOf(
    Proveedor("Ana García",     "Limpieza del hogar", 4.9f, "0.8 km", "12 min", "\$280/hr", "AG", Color(0xFF7B1FA2)),
    Proveedor("Luis Herrera",   "Mensajería express", 4.7f, "1.2 km", "15 min", "\$90",     "LH", Color(0xFF00838F)),
    Proveedor("Carlos Jiménez", "Plomería y gas",     4.8f, "2.1 km", "22 min", "\$450/hr", "CJ", Color(0xFFE65100)),
    Proveedor("Sofía Méndez",   "Mudanza y carga",    4.6f, "3.0 km", "30 min", "\$600",    "SM", Color(0xFF2E7D32)),
)

private val pedidosRecientes = listOf(
    PedidoReciente("Limpieza",   "Ana García",   "Hoy, 9:00 AM",  "\$560", "Completado", CerkaAcento, Icons.Default.Check),
    PedidoReciente("Mensajería", "Luis Herrera", "Ayer, 3:30 PM", "\$90",  "Cancelado",  CerkaRojo,   Icons.Default.Close),
    PedidoReciente("Plomería",   "C. Jiménez",   "12 may, 11:00", "\$900", "Completado", CerkaAcento, Icons.Default.Check),
)

private val categorias = listOf(
    Categoria("Mensajería",   Icons.Default.Email),
    Categoria("Mudanza",      Icons.Default.Home),
    Categoria("Limpieza",     Icons.Default.Star),
    Categoria("Plomería",     Icons.Default.Build),
    Categoria("Electricidad", Icons.Default.Warning),
    Categoria("Más",          Icons.Default.Add),
)

// --- Screen ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CerkaScreen(navController: NavController) {
    CerkaTema {
        Scaffold(
            containerColor = CerkaFondo,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    title = {
                        Column {
                            Text(
                                "Cerka",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                "📍 Colonia Del Valle, CDMX",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.75f)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", tint = Color.White)
                        }
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.20f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "JM",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = CerkaPrimario)
                )
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item { MapaSimulado() }
                item { TarjetaPedidoActivo(pedidoActivo) }
                item { SeccionCategorias() }
                item { SeccionProveedores() }
                item { SeccionPedidosRecientes() }
                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun MapaSimulado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color(0xFFE8EDF2))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridColor  = Color(0xFFCDD4DB)
            val roadColor  = Color.White
            val roadWidth  = 9.dp.toPx()
            val gridWidth  = 1.5.dp.toPx()
            val gridStep   = 52.dp.toPx()

            // Secondary grid
            var x = 0f
            while (x <= size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = gridWidth)
                x += gridStep
            }
            var y = 0f
            while (y <= size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = gridWidth)
                y += gridStep
            }

            // Main roads
            listOf(size.height * 0.28f, size.height * 0.56f, size.height * 0.82f).forEach { ry ->
                drawLine(roadColor, Offset(0f, ry), Offset(size.width, ry), strokeWidth = roadWidth)
            }
            listOf(size.width * 0.24f, size.width * 0.52f, size.width * 0.78f).forEach { rx ->
                drawLine(roadColor, Offset(rx, 0f), Offset(rx, size.height), strokeWidth = roadWidth)
            }

            // Route: active provider → user
            val userPos     = Offset(size.width * 0.52f, size.height * 0.56f)
            val providerPos = Offset(size.width * 0.24f, size.height * 0.28f)
            drawLine(
                color       = Color(0xFF311B92).copy(alpha = 0.65f),
                start       = providerPos,
                end         = userPos,
                strokeWidth = 5.dp.toPx(),
                cap         = StrokeCap.Round,
                pathEffect  = PathEffect.dashPathEffect(floatArrayOf(18f, 12f))
            )

            // Provider pins
            val pins = listOf(
                Pair(providerPos,                                              Color(0xFF7B1FA2)),
                Pair(Offset(size.width * 0.78f, size.height * 0.28f),        Color(0xFF00838F)),
                Pair(Offset(size.width * 0.18f, size.height * 0.72f),        Color(0xFFE65100)),
            )
            pins.forEach { (pos, tint) ->
                drawCircle(Color.White, radius = 13.dp.toPx(), center = pos)
                drawCircle(tint,        radius = 9.dp.toPx(),  center = pos)
                drawCircle(Color.White, radius = 3.5.dp.toPx(),center = pos)
            }

            // User pin with glow
            drawCircle(Color(0xFF311B92).copy(alpha = 0.15f), radius = 24.dp.toPx(), center = userPos)
            drawCircle(Color.White,                            radius = 17.dp.toPx(), center = userPos)
            drawCircle(Color(0xFF311B92),                      radius = 11.dp.toPx(), center = userPos)
            drawCircle(Color.White,                            radius = 4.5.dp.toPx(),center = userPos)
        }

        // ETA chip (top-end)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(CerkaPrimario)
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Text(
                "8 min · En camino",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        // "Ver mapa" chip (bottom-start)
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.92f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                "Ver mapa completo",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = CerkaPrimario
            )
        }
    }
}

@Composable
private fun TarjetaPedidoActivo(pedido: PedidoActivo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CerkaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Pedido activo",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = CerkaSubtexto
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(CerkaAcento.copy(alpha = 0.14f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        pedido.estado,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = CerkaAcento
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(CerkaPrimario.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        pedido.iniciales,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = CerkaPrimario
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        pedido.proveedor,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = CerkaOnFondo
                    )
                    Text(pedido.especialidad, style = MaterialTheme.typography.bodySmall, color = CerkaSubtexto)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        pedido.eta,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = CerkaPrimario
                    )
                    Text("ETA", style = MaterialTheme.typography.labelSmall, color = CerkaSubtexto)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Progreso del servicio", style = MaterialTheme.typography.labelSmall, color = CerkaSubtexto)
                    Text(
                        "${(pedido.progreso * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = CerkaPrimario
                    )
                }
                LinearProgressIndicator(
                    progress = { pedido.progreso },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = CerkaPrimario,
                    trackColor = CerkaPrimario.copy(alpha = 0.15f),
                    strokeCap = StrokeCap.Round
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, CerkaPrimario)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Llamar")
                }
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CerkaPrimario)
                ) {
                    Text("Ver detalle")
                }
            }
        }
    }
}

@Composable
private fun SeccionCategorias() {
    Column(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
        Text(
            "Categorías",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = CerkaOnFondo,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categorias) { ChipCategoria(it) }
        }
    }
}

@Composable
private fun ChipCategoria(cat: Categoria) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CerkaPrimario.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(cat.icono, contentDescription = null, tint = CerkaPrimario, modifier = Modifier.size(26.dp))
        }
        Text(cat.nombre, style = MaterialTheme.typography.labelSmall, color = CerkaOnFondo)
    }
}

@Composable
private fun SeccionProveedores() {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Proveedores cercanos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = CerkaOnFondo
            )
            TextButton(onClick = {}) {
                Text("Ver todos", style = MaterialTheme.typography.labelMedium, color = CerkaPrimario)
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(proveedores) { TarjetaProveedor(it) }
        }
    }
}

@Composable
private fun TarjetaProveedor(prov: Proveedor) {
    Card(
        modifier = Modifier.width(182.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CerkaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(prov.colorAvatar),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        prov.iniciales,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        prov.nombre,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = CerkaOnFondo,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = CerkaAmbar, modifier = Modifier.size(12.dp))
                        Text(
                            "${prov.rating}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = CerkaOnFondo
                        )
                    }
                }
            }

            Text(
                prov.especialidad,
                style = MaterialTheme.typography.bodySmall,
                color = CerkaSubtexto,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            HorizontalDivider(color = Color(0xFFF0F0F0))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    Pair(prov.distancia, "dist."),
                    Pair(prov.eta,       "ETA"),
                    Pair(prov.precio,    "precio"),
                ).forEach { (valor, etiqueta) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            valor,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (etiqueta == "precio") CerkaPrimario else CerkaOnFondo
                        )
                        Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = CerkaSubtexto)
                    }
                }
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CerkaPrimario),
                contentPadding = PaddingValues(vertical = 6.dp)
            ) {
                Text("Solicitar", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun SeccionPedidosRecientes() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Mis pedidos recientes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = CerkaOnFondo
            )
            TextButton(onClick = {}) {
                Text("Ver historial", style = MaterialTheme.typography.labelMedium, color = CerkaPrimario)
            }
        }
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CerkaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pedidosRecientes.forEachIndexed { i, pedido ->
                    FilaPedido(pedido)
                    if (i < pedidosRecientes.size - 1) HorizontalDivider(color = Color(0xFFF0F0F0))
                }
            }
        }
    }
}

@Composable
private fun FilaPedido(pedido: PedidoReciente) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(pedido.colorEstado.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(pedido.icono, contentDescription = null, tint = pedido.colorEstado, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                pedido.servicio,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = CerkaOnFondo
            )
            Text(
                "${pedido.proveedor} · ${pedido.fecha}",
                style = MaterialTheme.typography.labelSmall,
                color = CerkaSubtexto
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                pedido.monto,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = CerkaOnFondo
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(pedido.colorEstado.copy(alpha = 0.12f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    pedido.estado,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = pedido.colorEstado
                )
            }
        }
    }
}
