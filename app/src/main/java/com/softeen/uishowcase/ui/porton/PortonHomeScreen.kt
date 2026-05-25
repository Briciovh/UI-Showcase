package com.softeen.uishowcase.ui.porton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// ── Private palette ───────────────────────────────────────────────────────────
private val PortonFondo     = Color(0xFFF5F6FA)
private val PortonPrimario  = Color(0xFF1A5276)
private val PortonAccento   = Color(0xFFE67E22)
private val PortonVerde     = Color(0xFF27AE60)
private val PortonRojo      = Color(0xFFE74C3C)
private val PortonSurface   = Color(0xFFFFFFFF)
private val PortonSubtext   = Color(0xFF7F8C8D)
private val PortonOnBg      = Color(0xFF17202A)
private val PortonStroke    = Color(0xFFDFE6E9)

// ── Theme ─────────────────────────────────────────────────────────────────────
@Composable
private fun PortonTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary      = PortonPrimario,
            background   = PortonFondo,
            surface      = PortonSurface,
            onBackground = PortonOnBg,
            onSurface    = PortonOnBg,
            onPrimary    = Color.White,
        ),
        content = content
    )
}

// ── State machine ─────────────────────────────────────────────────────────────
private enum class EstadoPuerta { CERRADA, ABRIENDO, ABIERTA, ERROR }

// ── Data ──────────────────────────────────────────────────────────────────────
private data class RegistroAcceso(
    val nombre: String,
    val apartamento: String,
    val hora: String,
    val fecha: String,
    val colorAvatar: Color
)

private val historial = listOf(
    RegistroAcceso("Carlos Martínez", "4B", "08:42", "Hoy",  PortonPrimario),
    RegistroAcceso("Laura Sánchez",   "2A", "07:55", "Hoy",  Color(0xFF8E44AD)),
    RegistroAcceso("Pedro Gómez",     "1C", "23:10", "Ayer", Color(0xFF2C3E50)),
    RegistroAcceso("Ana Torres",      "3D", "19:30", "Ayer", Color(0xFF16A085)),
)

// ── Screen ────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortonHomeScreen(navController: NavController) {
    var estado by remember { mutableStateOf(EstadoPuerta.CERRADA) }

    // State machine: ABRIENDO → ABIERTA → CERRADA
    LaunchedEffect(estado) {
        if (estado == EstadoPuerta.ABRIENDO) {
            delay(1500L)
            estado = EstadoPuerta.ABIERTA
            delay(2500L)
            estado = EstadoPuerta.CERRADA
        }
    }

    PortonTema {
        Scaffold(
            containerColor = PortonFondo,
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("🏢", fontSize = 18.sp)
                            Text(
                                "Portón",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.80f)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PortonPrimario)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    TarjetaBienvenida(
                        estado = estado,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                item { Spacer(Modifier.height(8.dp)) }
                item {
                    SeccionBotonPuerta(
                        estado = estado,
                        onTap = { estado = EstadoPuerta.ABRIENDO }
                    )
                }
                item { Spacer(Modifier.height(8.dp)) }
                item {
                    TarjetaEstadoAPI(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
                item {
                    SeccionHistorial(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

// ── Welcome card ──────────────────────────────────────────────────────────────
@Composable
private fun TarjetaBienvenida(estado: EstadoPuerta, modifier: Modifier = Modifier) {
    val (pillColor, pillLabel) = when (estado) {
        EstadoPuerta.CERRADA   -> PortonRojo    to "🔴 Cerrada"
        EstadoPuerta.ABRIENDO  -> PortonAccento to "🟡 Abriendo…"
        EstadoPuerta.ABIERTA   -> PortonVerde   to "🟢 Abierta"
        EstadoPuerta.ERROR     -> PortonRojo    to "🔴 Error"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PortonSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    "Bienvenido,",
                    style = MaterialTheme.typography.bodySmall,
                    color = PortonSubtext
                )
                Text(
                    "Carlos Martínez",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = PortonPrimario
                )
                Text(
                    "Apt. 4B · Edificio San Marco",
                    style = MaterialTheme.typography.labelSmall,
                    color = PortonSubtext
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(pillColor.copy(alpha = 0.13f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    pillLabel,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = pillColor
                )
            }
        }
    }
}

// ── Door button ───────────────────────────────────────────────────────────────
@Composable
private fun SeccionBotonPuerta(estado: EstadoPuerta, onTap: () -> Unit) {
    val buttonColor = when (estado) {
        EstadoPuerta.CERRADA   -> PortonAccento
        EstadoPuerta.ABRIENDO  -> Color(0xFFBDC3C7)
        EstadoPuerta.ABIERTA   -> PortonVerde
        EstadoPuerta.ERROR     -> PortonRojo
    }
    val sublabel = when (estado) {
        EstadoPuerta.CERRADA   -> "Toca para abrir el portal"
        EstadoPuerta.ABRIENDO  -> "Enviando comando a la API…"
        EstadoPuerta.ABIERTA   -> "El portal está abierto"
        EstadoPuerta.ERROR     -> "Vuelve a intentarlo"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        PortonFondo,
                        PortonPrimario.copy(alpha = 0.06f),
                        PortonFondo
                    )
                )
            )
            .padding(vertical = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Outer glow ring (only visible in idle state)
            Box(
                modifier = Modifier
                    .size(196.dp)
                    .clip(CircleShape)
                    .background(
                        if (estado == EstadoPuerta.CERRADA) PortonAccento.copy(alpha = 0.11f)
                        else Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner button
                Card(
                    onClick = { if (estado == EstadoPuerta.CERRADA) onTap() },
                    modifier = Modifier.size(160.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = buttonColor),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (estado) {
                            EstadoPuerta.CERRADA -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(42.dp)
                                    )
                                    Text(
                                        "Abrir\nPuerta",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 15.sp
                                        ),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            EstadoPuerta.ABRIENDO -> {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 3.dp,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                            EstadoPuerta.ABIERTA -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(42.dp)
                                    )
                                    Text(
                                        "¡Abierta!",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 15.sp
                                        ),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            EstadoPuerta.ERROR -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(42.dp)
                                    )
                                    Text(
                                        "Error",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.ExtraBold
                                        ),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Text(
                sublabel,
                style = MaterialTheme.typography.bodySmall,
                color = PortonSubtext,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── API status card ───────────────────────────────────────────────────────────
@Composable
private fun TarjetaEstadoAPI(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PortonSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Conexión con el dispositivo",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = PortonSubtext
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(PortonVerde)
                )
                Text(
                    "api.sanmarco.com/v2/door",
                    style = MaterialTheme.typography.bodySmall,
                    color = PortonPrimario,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "42 ms",
                    style = MaterialTheme.typography.labelSmall,
                    color = PortonSubtext
                )
            }
            HorizontalDivider(color = PortonStroke)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Estado del servicio",
                    style = MaterialTheme.typography.labelSmall,
                    color = PortonSubtext
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PortonVerde.copy(alpha = 0.13f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        "Conectado",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = PortonVerde
                    )
                }
            }
        }
    }
}

// ── Access history ────────────────────────────────────────────────────────────
@Composable
private fun SeccionHistorial(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Historial de accesos",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PortonOnBg
            )
            Text(
                "Ver todo",
                style = MaterialTheme.typography.labelMedium,
                color = PortonPrimario
            )
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PortonSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                historial.forEachIndexed { i, acceso ->
                    FilaRegistroAcceso(acceso)
                    if (i < historial.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = PortonStroke
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaRegistroAcceso(acceso: RegistroAcceso) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(acceso.colorAvatar),
            contentAlignment = Alignment.Center
        ) {
            Text(
                acceso.nombre.first().toString(),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                acceso.nombre,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                color = PortonOnBg,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "Apt. ${acceso.apartamento}",
                style = MaterialTheme.typography.labelSmall,
                color = PortonSubtext
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                acceso.hora,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = PortonPrimario
            )
            Text(
                acceso.fecha,
                style = MaterialTheme.typography.labelSmall,
                color = PortonSubtext
            )
        }
    }
}
