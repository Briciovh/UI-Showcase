package com.softeen.uishowcase.ui.vortex

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.softeen.uishowcase.R
import com.softeen.uishowcase.navigation.AppRoutes

// ── Private palette ───────────────────────────────────────────────────────────
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
private data class ConvItem(
    val nombre: String,
    val ultimoMensaje: String,
    val hora: String,
    val noLeidos: Int,
    val online: Boolean,
    val fijado: Boolean,
    val silenciado: Boolean,
    val colorAvatar: Color,
    val esGrupo: Boolean = false,
    val estuveYo: Boolean = false
)

private data class UserActivo(val nombre: String, val color: Color)

private val activosAhora = listOf(
    UserActivo("Alejandra", Color(0xFF7C3AED)),
    UserActivo("Carlos",    Color(0xFF0891B2)),
    UserActivo("Sofía",     Color(0xFFDB2777)),
    UserActivo("Miguel",    Color(0xFF059669)),
    UserActivo("Valeria",   Color(0xFFD97706)),
    UserActivo("Renata",    Color(0xFFDC2626)),
)

private val chats = listOf(
    ConvItem(
        nombre = "Alejandra",
        ultimoMensaje = "¡Claro! ¿A qué hora nos vemos? 😊",
        hora = "ahora", noLeidos = 3, online = true,
        fijado = true, silenciado = false, colorAvatar = Color(0xFF7C3AED)
    ),
    ConvItem(
        nombre = "Dev Team 🚀",
        ultimoMensaje = "Carlos: El build pasó ✅ deploying...",
        hora = "2 min", noLeidos = 7, online = false,
        fijado = true, silenciado = false, colorAvatar = Color(0xFF0891B2), esGrupo = true
    ),
    ConvItem(
        nombre = "Sofía Méndez",
        ultimoMensaje = "Perfecto, nos vemos mañana 👍",
        hora = "14:32", noLeidos = 0, online = true,
        fijado = false, silenciado = false, colorAvatar = Color(0xFFDB2777), estuveYo = true
    ),
    ConvItem(
        nombre = "Miguel Torres",
        ultimoMensaje = "¿Ya viste el partido anoche? 🔥",
        hora = "12:15", noLeidos = 1, online = false,
        fijado = false, silenciado = false, colorAvatar = Color(0xFF059669)
    ),
    ConvItem(
        nombre = "Familia 🏠",
        ultimoMensaje = "Mamá: El domingo a las 2 ✅",
        hora = "ayer", noLeidos = 0, online = false,
        fijado = false, silenciado = true, colorAvatar = Color(0xFFD97706), esGrupo = true
    ),
    ConvItem(
        nombre = "Valeria Ortiz",
        ultimoMensaje = "Gracias por el dato 🙏",
        hora = "ayer", noLeidos = 0, online = false,
        fijado = false, silenciado = false, colorAvatar = Color(0xFF9333EA), estuveYo = true
    ),
    ConvItem(
        nombre = "Renata Ríos",
        ultimoMensaje = "Ok, te mando el archivo 📎",
        hora = "lun", noLeidos = 0, online = false,
        fijado = false, silenciado = true, colorAvatar = Color(0xFFDC2626)
    ),
)

// ── Screen ────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VortexListScreen(navController: NavController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.vortex_tab_all),
        stringResource(R.string.vortex_tab_unread),
        stringResource(R.string.vortex_tab_groups),
    )

    VortexTema {
        Scaffold(
            containerColor = VxFondo,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.common_back),
                                tint = VxPrimario
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(
                                        Brush.linearGradient(listOf(VxPrimario, VxAcento)),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "V",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 15.sp
                                )
                            }
                            Text(
                                stringResource(R.string.vortex_title),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = VxSubtext)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = VxSubtext)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = VxFondo)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    containerColor = VxPrimario,
                    contentColor = VxFondo,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.vortex_new_chat))
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            ) {
                item { BuscadorVortex() }
                item { SeccionActivos() }
                item {
                    PrimaryTabRow(
                        selectedTabIndex = tabIndex,
                        containerColor = VxFondo,
                        contentColor = VxPrimario,
                        divider = { HorizontalDivider(color = VxStroke, thickness = 0.5.dp) }
                    ) {
                        tabs.forEachIndexed { i, label ->
                            Tab(
                                selected = tabIndex == i,
                                onClick = { tabIndex = i },
                                selectedContentColor = VxPrimario,
                                unselectedContentColor = VxSubtext,
                                text = {
                                    Text(
                                        label,
                                        fontWeight = if (tabIndex == i) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 13.sp
                                    )
                                }
                            )
                        }
                    }
                }

                val pinned   = chats.filter { it.fijado }
                val unpinned = chats.filter { !it.fijado }

                if (pinned.isNotEmpty()) {
                    item { SectionLabel(stringResource(R.string.vortex_pinned)) }
                    items(pinned) { chat ->
                        FilaChat(chat) { navController.navigate(AppRoutes.VORTEX_CHAT) }
                    }
                }
                item { SectionLabel(stringResource(R.string.vortex_recent)) }
                items(unpinned) { chat ->
                    FilaChat(chat) { navController.navigate(AppRoutes.VORTEX_CHAT) }
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

// ── Components ────────────────────────────────────────────────────────────────
@Composable
private fun BuscadorVortex() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(VxSurface2)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(Icons.Default.Search, contentDescription = null, tint = VxSubtext, modifier = Modifier.size(18.dp))
        Text(stringResource(R.string.vortex_search_placeholder), style = MaterialTheme.typography.bodyMedium, color = VxSubtext)
    }
}

@Composable
private fun SeccionActivos() {
    Column(modifier = Modifier.padding(bottom = 4.dp)) {
        Text(
            stringResource(R.string.vortex_active_now),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = VxSubtext,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(activosAhora) { user -> BurbujaActivo(user) }
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = VxStroke, thickness = 0.5.dp)
    }
}

@Composable
private fun BurbujaActivo(user: UserActivo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(listOf(VxPrimario, VxAcento)),
                        shape = CircleShape
                    )
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(user.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    user.nombre.first().toString().uppercase(),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
            }
            // Online dot with border
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(VxFondo)
                    .padding(2.5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(VxOnline)
                )
            }
        }
        Text(
            user.nombre.split(" ").first(),
            style = MaterialTheme.typography.labelSmall,
            color = VxSubtext,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
        color = VxSubtext,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun FilaChat(conv: ConvItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        Box(contentAlignment = Alignment.BottomEnd) {
            if (conv.esGrupo) {
                GrupoAvatar(conv.colorAvatar)
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(conv.colorAvatar),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        conv.nombre.first().toString().uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                }
            }
            if (conv.online && !conv.esGrupo) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(VxFondo)
                        .padding(2.5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(VxOnline)
                    )
                }
            }
        }

        // Text content
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        conv.nombre,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (conv.noLeidos > 0) FontWeight.ExtraBold else FontWeight.SemiBold
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (conv.silenciado) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = VxSubtext.copy(alpha = 0.55f),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Text(
                    conv.hora,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (conv.noLeidos > 0 && !conv.silenciado) VxPrimario else VxSubtext
                )
            }
            Spacer(Modifier.height(3.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (conv.estuveYo) "Tú: ${conv.ultimoMensaje}" else conv.ultimoMensaje,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (conv.noLeidos > 0) Color.White.copy(alpha = 0.78f) else VxSubtext,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                if (conv.noLeidos > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(if (conv.silenciado) VxSurface2 else VxPrimario),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${conv.noLeidos}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp
                            ),
                            color = if (conv.silenciado) VxSubtext else VxFondo
                        )
                    }
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 84.dp),
        color = VxStroke,
        thickness = 0.4.dp
    )
}

@Composable
private fun GrupoAvatar(color: Color) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(listOf(color.copy(alpha = 0.45f), color.copy(alpha = 0.15f)))
            )
            .border(1.dp, color.copy(alpha = 0.38f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Person, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
    }
}
