package com.softeen.uishowcase.ui.sentry

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.softeen.uishowcase.R

// ── Private palette ───────────────────────────────────────────────────────────
private val VigiaFondo      = Color(0xFFF0F4FF)
private val VigiaPrimario   = Color(0xFF1565C0)
private val VigiaSeguro     = Color(0xFF2E7D32)
private val VigiaAlerta     = Color(0xFFE65100)
private val VigiaEmergencia = Color(0xFFC62828)
private val VigiaAmbar      = Color(0xFFF57F17)
private val VigiaSurface    = Color(0xFFFFFFFF)
private val VigiaSubtext    = Color(0xFF78909C)
private val VigiaOnBg       = Color(0xFF1A2C4A)
private val VigiaStroke     = Color(0xFFEEF2FF)

// ── Theme ─────────────────────────────────────────────────────────────────────
@Composable
private fun VigiaTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary      = VigiaPrimario,
            background   = VigiaFondo,
            surface      = VigiaSurface,
            onBackground = VigiaOnBg,
            onSurface    = VigiaOnBg,
            onPrimary    = Color.White,
        ),
        content = content
    )
}

// ── Enums ─────────────────────────────────────────────────────────────────────
private enum class EstadoZona { EN_ZONA_SEGURA, EN_TRANSITO, FUERA_DE_ZONA }
private enum class TipoAlerta { ENTRADA, SALIDA, BATERIA, EMERGENCIA }

// ── Data Classes ──────────────────────────────────────────────────────────────
private data class PerfilHijo(
    val nombre: String,
    val edad: Int,
    val ultimaUbicacion: String,
    val horaActualizacion: String,
    val estadoZona: EstadoZona,
    val bateria: Int,
    val colorAvatar: Color
)

private data class ZonaSegura(
    val nombre: String,
    val radio: String,
    val activa: Boolean,
    val icono: ImageVector,
    val colorZona: Color
)

private data class AlertaReciente(
    val mensaje: String,
    val hora: String,
    val tipo: TipoAlerta,
    val hijo: String
)

// ── Fake Data ─────────────────────────────────────────────────────────────────
private val hijos = listOf(
    PerfilHijo(
        nombre = "Sofía",
        edad = 8,
        ultimaUbicacion = "Escuela Primaria Hidalgo",
        horaActualizacion = "Hace 2 min",
        estadoZona = EstadoZona.EN_ZONA_SEGURA,
        bateria = 78,
        colorAvatar = Color(0xFF1565C0)
    ),
    PerfilHijo(
        nombre = "Diego",
        edad = 12,
        ultimaUbicacion = "En tránsito — Av. Central",
        horaActualizacion = "Hace 5 min",
        estadoZona = EstadoZona.EN_TRANSITO,
        bateria = 45,
        colorAvatar = Color(0xFF2E7D32)
    ),
    PerfilHijo(
        nombre = "Luna",
        edad = 6,
        ultimaUbicacion = "Casa",
        horaActualizacion = "Ahora",
        estadoZona = EstadoZona.EN_ZONA_SEGURA,
        bateria = 92,
        colorAvatar = Color(0xFF6A1B9A)
    ),
)

private val zonasSeguras = listOf(
    ZonaSegura("Casa",                     "150 m", true,  Icons.Default.Home, VigiaSeguro),
    ZonaSegura("Escuela Primaria Hidalgo", "200 m", true,  Icons.Default.Star, VigiaPrimario),
    ZonaSegura("Parque Independencia",     "100 m", true,  Icons.Default.Star, VigiaAmbar),
)

private val alertas = listOf(
    AlertaReciente("Entró a la zona de la escuela", "08:15",      TipoAlerta.ENTRADA,    "Sofía"),
    AlertaReciente("Salió de casa",                 "07:50",      TipoAlerta.SALIDA,     "Diego"),
    AlertaReciente("Batería baja (15%)",             "07:30",      TipoAlerta.BATERIA,    "Luna"),
    AlertaReciente("Fuera de zona segura 12 min",   "Ayer 18:42", TipoAlerta.EMERGENCIA, "Diego"),
)

// ── Screen ────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentryScreen(navController: NavController) {
    VigiaTema {
        Scaffold(
            containerColor = VigiaFondo,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.common_back),
                                tint = Color.White
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("🛡️", fontSize = 20.sp)
                            Text(
                                stringResource(R.string.sentry_title),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = VigiaPrimario)
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.Warning, contentDescription = null)
                    },
                    text = {
                        Text(stringResource(R.string.sentry_emergency), fontWeight = FontWeight.Bold)
                    },
                    containerColor = VigiaEmergencia,
                    contentColor = Color.White
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                item { MapaSeguridad() }
                item { SeccionHijos() }
                item { SeccionZonasSeguras() }
                item { SeccionAlertas() }
                item { SeccionEstadisticas() }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

// ── Map ───────────────────────────────────────────────────────────────────────
@Composable
private fun MapaSeguridad() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(248.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Map background
            drawRect(Color(0xFFDCE8F5))

            // Street grid — horizontal + vertical
            val gridColor = Color.White.copy(alpha = 0.55f)
            val gridStroke = 1.5.dp.toPx()
            for (i in 1..4) {
                drawLine(gridColor, Offset(0f, h * i / 5f), Offset(w, h * i / 5f), gridStroke)
                drawLine(gridColor, Offset(w * i / 5f, 0f), Offset(w * i / 5f, h), gridStroke)
            }

            // ── Safe zone: Escuela (upper-right) — blue ───────────────────────
            val escuelaC = Offset(w * 0.73f, h * 0.27f)
            val escuelaR = 52.dp.toPx()
            drawCircle(VigiaPrimario.copy(alpha = 0.11f), escuelaR, escuelaC)
            drawCircle(VigiaPrimario.copy(alpha = 0.38f), escuelaR, escuelaC, style = Stroke(1.8.dp.toPx()))

            // ── Safe zone: Casa (center-left) — green ─────────────────────────
            val casaC = Offset(w * 0.37f, h * 0.57f)
            val casaR = 66.dp.toPx()
            drawCircle(VigiaSeguro.copy(alpha = 0.12f), casaR, casaC)
            drawCircle(VigiaSeguro.copy(alpha = 0.42f), casaR, casaC, style = Stroke(1.8.dp.toPx()))

            // ── Luna's pin — inside Casa zone, violet ─────────────────────────
            val lunaC = Offset(w * 0.30f, h * 0.60f)
            drawCircle(Color(0xFF6A1B9A).copy(alpha = 0.22f), 18.dp.toPx(), lunaC)
            drawCircle(Color(0xFF6A1B9A), 9.dp.toPx(), lunaC)
            drawCircle(Color.White.copy(alpha = 0.35f), 9.dp.toPx(), lunaC, style = Stroke(1.2.dp.toPx()))

            // ── Sofía's pin — inside Escuela zone, green glow ─────────────────
            val sofiaC = Offset(w * 0.70f, h * 0.28f)
            drawCircle(VigiaSeguro.copy(alpha = 0.17f), 22.dp.toPx(), sofiaC)
            drawCircle(VigiaSeguro.copy(alpha = 0.32f), 14.dp.toPx(), sofiaC)
            drawCircle(VigiaSeguro, 9.dp.toPx(), sofiaC)
            drawCircle(Color.White.copy(alpha = 0.35f), 9.dp.toPx(), sofiaC, style = Stroke(1.2.dp.toPx()))

            // ── Diego's pin — in transit, amber / outside ─────────────────────
            val diegoC = Offset(w * 0.54f, h * 0.74f)
            drawCircle(VigiaAlerta.copy(alpha = 0.22f), 20.dp.toPx(), diegoC)
            drawCircle(VigiaAlerta, 9.dp.toPx(), diegoC)
            drawCircle(Color.White.copy(alpha = 0.35f), 9.dp.toPx(), diegoC, style = Stroke(1.2.dp.toPx()))

            // ── Home marker (small solid pin at center of Casa zone) ──────────
            drawCircle(VigiaPrimario, 5.5.dp.toPx(), casaC)
            drawCircle(Color.White, 5.5.dp.toPx(), casaC, style = Stroke(1.2.dp.toPx()))
        }

        // Status overlay chip
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.92f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                "🟢 2 en zona  ·  🟡 1 en tránsito",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = VigiaOnBg
            )
        }

        // "En vivo" chip top-right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(VigiaPrimario.copy(alpha = 0.88f))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF69FF47))
                )
                Text(
                    stringResource(R.string.sentry_live),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

// ── Children Section ──────────────────────────────────────────────────────────
@Composable
private fun SeccionHijos() {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.sentry_my_children),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = VigiaOnBg
            )
            Text(stringResource(R.string.sentry_manage), style = MaterialTheme.typography.labelMedium, color = VigiaPrimario)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(hijos) { hijo -> TarjetaHijo(hijo) }
        }
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun TarjetaHijo(hijo: PerfilHijo) {
    val batteryColor = when {
        hijo.bateria > 50 -> VigiaSeguro
        hijo.bateria > 20 -> VigiaAmbar
        else              -> VigiaEmergencia
    }
    Card(
        modifier = Modifier.width(178.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VigiaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(hijo.colorAvatar),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        hijo.nombre.first().toString(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
                BadgeEstadoZona(hijo.estadoZona)
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    hijo.nombre,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = VigiaOnBg
                )
                Text(stringResource(R.string.sentry_years_old, hijo.edad), style = MaterialTheme.typography.labelSmall, color = VigiaSubtext)
            }
            Text(
                hijo.ultimaUbicacion,
                style = MaterialTheme.typography.bodySmall,
                color = VigiaSubtext,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "🔋 ${hijo.bateria}%",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = batteryColor
                    )
                    Text(
                        hijo.horaActualizacion,
                        style = MaterialTheme.typography.labelSmall,
                        color = VigiaSubtext
                    )
                }
                LinearProgressIndicator(
                    progress = { hijo.bateria / 100f },
                    modifier = Modifier.fillMaxWidth(),
                    color = batteryColor,
                    trackColor = batteryColor.copy(alpha = 0.18f),
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
private fun BadgeEstadoZona(estado: EstadoZona) {
    val (text, color) = when (estado) {
        EstadoZona.EN_ZONA_SEGURA -> stringResource(R.string.sentry_in_zone)    to VigiaSeguro
        EstadoZona.EN_TRANSITO    -> stringResource(R.string.sentry_in_transit) to VigiaAmbar
        EstadoZona.FUERA_DE_ZONA  -> stringResource(R.string.sentry_out_of_zone) to VigiaEmergencia
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = color
        )
    }
}

// ── Safe Zones Section ────────────────────────────────────────────────────────
@Composable
private fun SeccionZonasSeguras() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.sentry_safe_zones),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = VigiaOnBg
            )
            Text(stringResource(R.string.sentry_add_zone), style = MaterialTheme.typography.labelMedium, color = VigiaPrimario)
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = VigiaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                zonasSeguras.forEachIndexed { i, zona ->
                    FilaZonaSegura(zona)
                    if (i < zonasSeguras.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = VigiaStroke
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaZonaSegura(zona: ZonaSegura) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(zona.colorZona.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                zona.icono,
                contentDescription = null,
                tint = zona.colorZona,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                zona.nombre,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = VigiaOnBg,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(stringResource(R.string.sentry_radius, zona.radio), style = MaterialTheme.typography.labelSmall, color = VigiaSubtext)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (zona.activa) VigiaSeguro.copy(alpha = 0.13f) else Color(0xFFEEEEEE))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                if (zona.activa) stringResource(R.string.sentry_zone_active) else stringResource(R.string.sentry_zone_inactive),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = if (zona.activa) VigiaSeguro else VigiaSubtext
            )
        }
    }
}

// ── Alerts Section ────────────────────────────────────────────────────────────
@Composable
private fun SeccionAlertas() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.sentry_recent_alerts),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = VigiaOnBg
            )
            Text(stringResource(R.string.sentry_view_all), style = MaterialTheme.typography.labelMedium, color = VigiaPrimario)
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = VigiaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                alertas.forEachIndexed { i, alerta ->
                    FilaAlerta(alerta)
                    if (i < alertas.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = VigiaStroke
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaAlerta(alerta: AlertaReciente) {
    val (color, emoji) = when (alerta.tipo) {
        TipoAlerta.ENTRADA    -> VigiaSeguro     to "✅"
        TipoAlerta.SALIDA     -> VigiaAmbar      to "🚶"
        TipoAlerta.BATERIA    -> VigiaAlerta     to "🔋"
        TipoAlerta.EMERGENCIA -> VigiaEmergencia to "🚨"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.13f)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 16.sp)
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                alerta.hijo,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
            )
            Text(
                alerta.mensaje,
                style = MaterialTheme.typography.bodySmall,
                color = VigiaOnBg,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(alerta.hora, style = MaterialTheme.typography.labelSmall, color = VigiaSubtext)
    }
}

// ── Statistics Section ────────────────────────────────────────────────────────
@Composable
private fun SeccionEstadisticas() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            stringResource(R.string.sentry_stats_today),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = VigiaOnBg,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TarjetaEstadistica("🕐", stringResource(R.string.sentry_in_zone),    "6h 20m", Modifier.weight(1f))
            TarjetaEstadistica("📍", stringResource(R.string.sentry_stat_trips), "3",       Modifier.weight(1f))
            TarjetaEstadistica("🔋", stringResource(R.string.sentry_stat_battery),"71%",    Modifier.weight(1f))
        }
    }
}

@Composable
private fun TarjetaEstadistica(
    emoji: String,
    label: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = VigiaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(emoji, fontSize = 22.sp)
            Text(
                valor,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                color = VigiaPrimario
            )
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = VigiaSubtext,
                textAlign = TextAlign.Center
            )
        }
    }
}
