package com.softeen.uishowcase.ui.vortex

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ── Private palette (file-private; same values as VortexListScreen) ───────────
private val VxFondo    = Color(0xFF080C18)
private val VxPrimario = Color(0xFF00D4FF)
private val VxAcento   = Color(0xFF7C3AED)
private val VxSurface  = Color(0xFF111827)
private val VxSurface2 = Color(0xFF1C2333)
private val VxSubtext  = Color(0xFF8B9DC3)
private val VxOnline   = Color(0xFF10B981)
private val VxStroke   = Color(0xFF252D40)

// ── Theme ─────────────────────────────────────────────────────────────────────
@Composable
private fun VortexTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary      = VxPrimario,
            secondary    = VxAcento,
            background   = VxFondo,
            surface      = VxSurface,
            onBackground = Color.White,
            onSurface    = Color.White,
            onPrimary    = VxFondo,
        ),
        content = content
    )
}

// ── Data ──────────────────────────────────────────────────────────────────────
private data class MsgItem(
    val texto: String,
    val esPropio: Boolean,
    val hora: String,
    val leido: Boolean = true,
    val reaccion: String? = null,
    val esVoz: Boolean = false,
    val duracion: String? = null,
    val citando: String? = null,
    val esInfo: Boolean = false
)

private val mensajes = listOf(
    MsgItem(
        texto = "🔒  Los mensajes están cifrados de extremo a extremo",
        esPropio = false, hora = "", esInfo = true
    ),
    MsgItem(
        texto = "Oye! ¿Tienes planes para el fin de semana? 🙌",
        esPropio = false, hora = "11:02"
    ),
    MsgItem(
        texto = "Nada especial... ¿por qué? 👀",
        esPropio = true, hora = "11:04"
    ),
    MsgItem(
        texto = "Unos amigos organizan una reunión en el rooftop del Hotel Velázquez 🍹🌃",
        esPropio = false, hora = "11:05"
    ),
    MsgItem(
        texto = "¿Te apuntas?",
        esPropio = false, hora = "11:06", reaccion = "😍"
    ),
    MsgItem(
        texto = "Suena increíble!! ¿A qué hora empieza?",
        esPropio = true, hora = "11:07"
    ),
    MsgItem(
        texto = "", esPropio = false, hora = "11:08",
        esVoz = true, duracion = "0:34"
    ),
    MsgItem(
        texto = "Perfecto! Ahí estaré sin falta 😎🔥",
        esPropio = true, hora = "11:10"
    ),
    MsgItem(
        texto = "Te comparto la ubicación 📍",
        esPropio = false, hora = "11:11"
    ),
    MsgItem(
        texto = "Gracias! Ya la vi, está súper cerca de donde vivo 🙌",
        esPropio = true, hora = "11:13",
        citando = "Te comparto la ubicación 📍"
    ),
    MsgItem(
        texto = "¡Claro! ¿A qué hora nos vemos? 😊",
        esPropio = false, hora = "11:14", reaccion = "❤️"
    ),
)

// ── Screen ────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VortexChatScreen(navController: NavController) {
    VortexTema {
        Scaffold(
            containerColor = VxFondo,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = VxPrimario
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(contentAlignment = Alignment.BottomEnd) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF7C3AED)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "A",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.ExtraBold
                                        ),
                                        color = Color.White
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(VxSurface)
                                        .padding(2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(VxOnline)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    "Alejandra",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White
                                )
                                Text(
                                    "En línea",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = VxOnline
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = VxPrimario)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, contentDescription = null, tint = VxSubtext)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = VxSurface)
                )
            },
            bottomBar = { BarraInput() }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 12.dp,
                    bottom = innerPadding.calculateBottomPadding() + 8.dp,
                    start = 12.dp,
                    end = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item { SeparadorFecha("Viernes, 23 de mayo") }
                items(mensajes) { msg ->
                    when {
                        msg.esInfo -> MsgInfo(msg.texto)
                        msg.esVoz  -> MsgVoz(msg)
                        else       -> MsgBurbuja(msg)
                    }
                }
                item { Spacer(Modifier.height(4.dp)) }
            }
        }
    }
}

// ── Message composables ───────────────────────────────────────────────────────
@Composable
private fun SeparadorFecha(fecha: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = VxStroke)
        Text(fecha, style = MaterialTheme.typography.labelSmall, color = VxSubtext)
        HorizontalDivider(modifier = Modifier.weight(1f), color = VxStroke)
    }
}

@Composable
private fun MsgInfo(texto: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(VxSurface2)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                texto,
                style = MaterialTheme.typography.labelSmall,
                color = VxSubtext,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MsgBurbuja(msg: MsgItem) {
    val esPropio = msg.esPropio
    val bubbleShape = if (esPropio) {
        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (esPropio) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = if (esPropio) Alignment.End else Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Bubble
            Box(
                modifier = Modifier
                    .widthIn(max = 272.dp)
                    .clip(bubbleShape)
                    .background(
                        if (esPropio) {
                            Brush.linearGradient(listOf(VxAcento, Color(0xFF0EA5D4)))
                        } else {
                            Brush.linearGradient(listOf(VxSurface2, VxSurface2))
                        }
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Reply quote
                    if (msg.citando != null) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (esPropio) Color.White.copy(alpha = 0.14f) else VxStroke
                                )
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(26.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(VxPrimario)
                            )
                            Text(
                                msg.citando,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (esPropio) Color.White.copy(alpha = 0.78f) else VxSubtext,
                                maxLines = 2
                            )
                        }
                    }
                    // Message text
                    Text(
                        msg.texto,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    // Time + read receipt
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            msg.hora,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color.White.copy(alpha = 0.55f)
                        )
                        if (esPropio) {
                            Text(
                                if (msg.leido) "✓✓" else "✓",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = if (msg.leido) VxPrimario.copy(alpha = 0.88f)
                                        else Color.White.copy(alpha = 0.42f)
                            )
                        }
                    }
                }
            }
            // Reaction pill (outside the bubble)
            if (msg.reaccion != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(VxSurface)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(msg.reaccion, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun MsgVoz(msg: MsgItem) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .widthIn(min = 185.dp, max = 240.dp)
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp))
                .background(VxSurface2)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Play circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(VxPrimario.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Text("▶", color = VxPrimario, fontSize = 13.sp)
            }
            // Waveform bars
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(0.40f, 0.75f, 0.55f, 0.95f, 0.65f, 0.85f, 0.50f, 0.72f, 0.55f).forEach { h ->
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height((22 * h).dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(VxPrimario.copy(alpha = 0.58f))
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    msg.duracion ?: "0:00",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = VxSubtext
                )
                Text(
                    msg.hora,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = VxSubtext.copy(alpha = 0.70f)
                )
            }
        }
    }
}

// ── Input bar ─────────────────────────────────────────────────────────────────
@Composable
private fun BarraInput() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VxSurface)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = VxSubtext, modifier = Modifier.size(22.dp))
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(VxSurface2)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mensaje…", style = MaterialTheme.typography.bodyMedium, color = VxSubtext)
            Text("😊", fontSize = 18.sp)
        }
        // Send button with gradient
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(VxAcento, VxPrimario))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar", tint = Color.White, modifier = Modifier.size(20.dp))
        }
    }
}
