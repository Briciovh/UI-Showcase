package com.softeen.uishowcase.ui.chispa

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ── Private palette ──────────────────────────────────────────────────────────

private val ChispaFondo      = Color(0xFF0F0F1A)
private val ChispaPrimario   = Color(0xFFFF4B81)
private val ChispaAcento     = Color(0xFFFF8C42)
private val ChispaSuperLike  = Color(0xFF00CFFD)
private val ChispaSurface    = Color(0xFF1C1C2E)
private val ChispaSurface2   = Color(0xFF2A2A3E)
private val ChispaSubtexto   = Color(0xFF9E9EBE)
private val ChispaOnline     = Color(0xFF4CAF50)

// ── Theme ─────────────────────────────────────────────────────────────────────

@Composable
private fun ChispaTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary      = ChispaPrimario,
            secondary    = ChispaAcento,
            background   = ChispaFondo,
            surface      = ChispaSurface,
            onBackground = Color.White,
            onSurface    = Color.White,
            onPrimary    = Color.White,
        ),
        content = content
    )
}

// ── Data ──────────────────────────────────────────────────────────────────────

private data class PerfilCitas(
    val nombre: String,
    val edad: Int,
    val ciudad: String,
    val distanciaKm: String,
    val descripcion: String,
    val intereses: List<String>,
    val verificado: Boolean,
    val gradiente: List<Color>
)

private data class MatchCitas(
    val nombre: String,
    val esNuevo: Boolean,
    val colorAvatar: Color
)

private data class ConversacionCitas(
    val nombre: String,
    val ultimoMensaje: String,
    val hora: String,
    val noLeidos: Int,
    val online: Boolean,
    val colorAvatar: Color
)

private val perfiles = listOf(
    PerfilCitas(
        nombre = "Elena García",
        edad = 26,
        ciudad = "Palma de Mallorca",
        distanciaKm = "1.2 km",
        descripcion = "Fotógrafa · Amante del mar y los atardeceres 🌅",
        intereses = listOf("🏄 Surf", "📸 Foto", "🍷 Vinos"),
        verificado = true,
        gradiente = listOf(Color(0xFFE91E63), Color(0xFFFF5722))
    ),
    PerfilCitas(
        nombre = "Sofía Ruiz",
        edad = 29,
        ciudad = "Inca",
        distanciaKm = "8.5 km",
        descripcion = "Chef y foodie · Exploradora de mercados locales 🌿",
        intereses = listOf("🍳 Cocina", "🧘 Yoga", "✈️ Viajes"),
        verificado = false,
        gradiente = listOf(Color(0xFF3F51B5), Color(0xFF9C27B0))
    ),
    PerfilCitas(
        nombre = "Ana Torres",
        edad = 24,
        ciudad = "Alcúdia",
        distanciaKm = "15.3 km",
        descripcion = "Instructora de paddle surf · Arte y naturaleza 🌊",
        intereses = listOf("🤿 Buceo", "🎵 Música", "🎨 Arte"),
        verificado = false,
        gradiente = listOf(Color(0xFF00BCD4), Color(0xFF26A69A))
    )
)

private val matches = listOf(
    MatchCitas("Elena",   esNuevo = true,  colorAvatar = Color(0xFFE91E63)),
    MatchCitas("Martina", esNuevo = true,  colorAvatar = Color(0xFF9C27B0)),
    MatchCitas("Camila",  esNuevo = false, colorAvatar = Color(0xFF00BCD4)),
    MatchCitas("Sara",    esNuevo = false, colorAvatar = Color(0xFF4CAF50)),
    MatchCitas("Laura",   esNuevo = false, colorAvatar = Color(0xFFFF5722)),
    MatchCitas("Isabel",  esNuevo = false, colorAvatar = Color(0xFF3F51B5)),
)

private val conversaciones = listOf(
    ConversacionCitas(
        nombre = "Martina",
        ultimoMensaje = "¿Te gustan los atardeceres en Cap Formentor? 🌅",
        hora = "5 min",
        noLeidos = 2,
        online = true,
        colorAvatar = Color(0xFF9C27B0)
    ),
    ConversacionCitas(
        nombre = "Camila",
        ultimoMensaje = "¡Claro que sí! ¿Cuándo quedamos? 😊",
        hora = "1h",
        noLeidos = 0,
        online = false,
        colorAvatar = Color(0xFF00BCD4)
    ),
    ConversacionCitas(
        nombre = "Sara",
        ultimoMensaje = "Hola! Vi que también te gusta el buceo 🤿",
        hora = "3h",
        noLeidos = 1,
        online = true,
        colorAvatar = Color(0xFF4CAF50)
    ),
    ConversacionCitas(
        nombre = "Camila R.",
        ultimoMensaje = "Genial conocerte!",
        hora = "ayer",
        noLeidos = 0,
        online = false,
        colorAvatar = Color(0xFF795548)
    ),
)

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChispaScreen(navController: NavController) {
    ChispaTema {
        Scaffold(
            containerColor = ChispaFondo,
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
                            Text("🔥", fontSize = 20.sp)
                            Text(
                                "Chispa",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = ChispaPrimario
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = ChispaSubtexto)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Settings, contentDescription = null, tint = ChispaSubtexto)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = ChispaSurface)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                item { StackTarjetasPerfil() }
                item { BotonesAccion() }
                item { SeccionMatches() }
                item { SeccionMensajes() }
                item { SeccionPremium() }
                item { Spacer(Modifier.height(32.dp)) }
            }
        }
    }
}

// ── Card stack ────────────────────────────────────────────────────────────────

@Composable
private fun StackTarjetasPerfil() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        TarjetaPerfil(
            perfil = perfiles[2],
            modifier = Modifier
                .fillMaxSize()
                .rotate(6f)
                .scale(0.88f)
                .alpha(0.50f)
        )
        TarjetaPerfil(
            perfil = perfiles[1],
            modifier = Modifier
                .fillMaxSize()
                .rotate(-3f)
                .scale(0.94f)
                .alpha(0.75f)
        )
        TarjetaPerfil(
            perfil = perfiles[0],
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun TarjetaPerfil(perfil: PerfilCitas, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = ChispaSurface)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gradient background (simulates photo while no drawable is set)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(perfil.gradiente))
            )

            // Large decorative initial
            Text(
                text = perfil.nombre.first().toString().uppercase(),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 180.sp,
                fontWeight = FontWeight.Black,
                color = Color.White.copy(alpha = 0.12f)
            )

            // Scrim for readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.88f))
                        )
                    )
            )

            // Distance badge (top-end)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    "📍 ${perfil.distanciaKm}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
            }

            // Verified badge (top-start)
            if (perfil.verificado) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(ChispaPrimario.copy(alpha = 0.92f))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        "Verificada",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }

            // Bottom info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    "${perfil.nombre}, ${perfil.edad}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    "📍 ${perfil.ciudad}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.80f)
                )
                Text(
                    perfil.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.70f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    perfil.intereses.take(3).forEach { interes ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.20f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                interes,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── Action buttons ────────────────────────────────────────────────────────────

@Composable
private fun BotonesAccion() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(icon = Icons.Default.Close,    tint = ChispaSubtexto, background = ChispaSurface2, size = 64.dp)
        Spacer(Modifier.width(20.dp))
        ActionButton(icon = Icons.Default.Star,     tint = ChispaSuperLike, background = ChispaSurface2, size = 52.dp)
        Spacer(Modifier.width(20.dp))
        ActionButton(icon = Icons.Default.Favorite, tint = Color.White,    background = ChispaPrimario, size = 64.dp)
    }
}

@Composable
private fun ActionButton(icon: ImageVector, tint: Color, background: Color, size: Dp) {
    Card(
        modifier = Modifier.size(size),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = background),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(size * 0.42f)
            )
        }
    }
}

// ── Matches carousel ──────────────────────────────────────────────────────────

@Composable
private fun SeccionMatches() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "✨ Nuevos matches",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                "Ver todos",
                style = MaterialTheme.typography.labelMedium,
                color = ChispaPrimario
            )
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(matches) { BurbujaMatch(it) }
        }
    }
}

@Composable
private fun BurbujaMatch(match: MatchCitas) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            // Gradient ring
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(ChispaPrimario, ChispaAcento)),
                        shape = CircleShape
                    )
                    .padding(3.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(match.colorAvatar),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        match.nombre.first().toString().uppercase(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
            if (match.esNuevo) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ChispaPrimario)
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        "NEW",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 9.sp
                        ),
                        color = Color.White
                    )
                }
            }
        }
        Text(
            match.nombre.split(" ").first(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = Color.White
        )
    }
}

// ── Messages ──────────────────────────────────────────────────────────────────

@Composable
private fun SeccionMensajes() {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "💬 Mensajes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                "Ver todos",
                style = MaterialTheme.typography.labelMedium,
                color = ChispaPrimario
            )
        }
        Spacer(Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ChispaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                conversaciones.forEachIndexed { i, conv ->
                    FilaMensaje(conv)
                    if (i < conversaciones.size - 1) {
                        HorizontalDivider(color = Color.White.copy(alpha = 0.06f))
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaMensaje(conv: ConversacionCitas) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(conv.colorAvatar),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    conv.nombre.first().toString().uppercase(),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .size(13.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(if (conv.online) ChispaOnline else ChispaSubtexto.copy(alpha = 0.45f))
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                conv.nombre,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                conv.ultimoMensaje,
                style = MaterialTheme.typography.bodySmall,
                color = ChispaSubtexto,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                conv.hora,
                style = MaterialTheme.typography.labelSmall,
                color = ChispaSubtexto
            )
            if (conv.noLeidos > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(ChispaPrimario),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${conv.noLeidos}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ── Premium section ───────────────────────────────────────────────────────────

@Composable
private fun SeccionPremium() {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            "🔒 ¿Quién te gustó?",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ChispaSurface2),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ChispaPrimario.copy(alpha = 0.08f),
                                ChispaAcento.copy(alpha = 0.04f)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Locked avatar previews
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val likeColors = listOf(
                            Color(0xFFE91E63),
                            Color(0xFF9C27B0),
                            Color(0xFF3F51B5)
                        )
                        likeColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(color.copy(alpha = 0.55f), color.copy(alpha = 0.25f))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.80f),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            "3 personas te dieron Like",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            "Activa Premium para ver quién te gustó primero",
                            style = MaterialTheme.typography.bodySmall,
                            color = ChispaSubtexto,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ChispaPrimario)
                    ) {
                        Text(
                            "✨ Ver quién te gustó",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
