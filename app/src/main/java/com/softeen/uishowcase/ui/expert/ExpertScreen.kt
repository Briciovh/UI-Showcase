package com.softeen.uishowcase.ui.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softeen.uishowcase.R

// Private palette
private val PericiaFondo      = Color(0xFFF4F7FB)
private val PericiaPrimario   = Color(0xFF0C2D6B)
private val PericiaAzul       = Color(0xFF1565C0)
private val PericiaAnaranjado = Color(0xFFE65100)
private val PericiaVerde      = Color(0xFF2E7D32)
private val PericiaRojo       = Color(0xFFC62828)
private val PericiaSurface    = Color.White
private val PericiaSubtexto   = Color(0xFF6B7896)
private val PericiaOnFondo    = Color(0xFF1A1A2E)

@Composable
private fun PericiaTema(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary      = PericiaPrimario,
            secondary    = PericiaAzul,
            background   = PericiaFondo,
            surface      = PericiaSurface,
            onBackground = PericiaOnFondo,
            onSurface    = PericiaOnFondo,
            onPrimary    = Color.White,
        ),
        content = content
    )
}

// --- Data ---

private data class Expediente(
    val numero: String,
    val tipo: String,
    val asegurado: String,
    val etiquetaEstado: String,
    val colorEstado: Color,
    val progreso: Float,
    val monto: String,
    val icono: ImageVector
)

private data class Cita(
    val asegurado: String,
    val tipoCita: String,
    val dia: String,
    val hora: String,
    val expedienteId: String,
    val colorAvatar: Color
)

private data class Documento(val nombre: String, val recibido: Boolean)

private val expedientes = listOf(
    Expediente("EXP-2025-0041", "Siniestro vehicular",   "Carlos Mendoza",  "Activo",           PericiaAzul,       0.65f, "$45,000", Icons.Default.Build),
    Expediente("EXP-2025-0038", "Daño en hogar",         "María González",  "En revisión",      PericiaAnaranjado, 0.40f, "$28,500", Icons.Default.Home),
    Expediente("EXP-2025-0033", "Siniestro de salud",    "Roberto Sánchez", "Pendiente de doc.",PericiaRojo,       0.20f, "$12,000", Icons.Default.Favorite),
    Expediente("EXP-2025-0029", "Robo de bienes",        "Laura Torres",    "Resuelto",         PericiaVerde,      1.00f, "$67,300", Icons.Default.Star),
)

private val citas = listOf(
    Cita("Carlos Mendoza",  "Entrevista inicial",   "15 may", "10:30 AM", "EXP-2025-0041", Color(0xFF1565C0)),
    Cita("María González",  "Inspección técnica",   "17 may", "2:00 PM",  "EXP-2025-0038", Color(0xFFE65100)),
    Cita("Roberto Sánchez", "Entrega de documentos","20 may", "11:00 AM", "EXP-2025-0033", Color(0xFF7B1FA2)),
)

private val documentos = listOf(
    Documento("Reporte policial",          recibido = true),
    Documento("Póliza de seguro",          recibido = true),
    Documento("Estimado de reparación",    recibido = true),
    Documento("Identificación oficial",    recibido = false),
    Documento("Fotografías del siniestro", recibido = false),
)

// --- Screen ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertScreen(navController: NavController) {
    var filtroSeleccionado by remember { mutableStateOf(0) }
    val filtros = listOf(
        stringResource(R.string.expert_filter_all),
        stringResource(R.string.expert_filter_active),
        stringResource(R.string.expert_filter_review),
        stringResource(R.string.expert_filter_pending),
        stringResource(R.string.expert_filter_closed),
    )

    PericiaTema {
        Scaffold(
            containerColor = PericiaFondo,
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
                        Text(
                            stringResource(R.string.expert_title),
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Notifications, contentDescription = stringResource(R.string.common_notifications), tint = Color.White)
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
                                "AJ",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PericiaPrimario)
                )
            }
        ) { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item { BannerEstadisticas() }
                item { BarraBusqueda() }
                item { FiltrosExpediente(filtros, filtroSeleccionado) { filtroSeleccionado = it } }
                item { SeccionExpedientes() }
                item { SeccionCitas() }
                item { SeccionDocumentos() }
                item { SeccionEvaluacion() }
                item { Spacer(Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun BannerEstadisticas() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(listOf(PericiaPrimario, Color(0xFF1A4FA8))))
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Bienvenido, Lic. Aguilar",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.80f)
            )
            Text(
                stringResource(R.string.expert_dashboard),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChipStat("12", stringResource(R.string.expert_stat_total),   Modifier.weight(1f))
                ChipStat("4",  stringResource(R.string.expert_stat_active),  Modifier.weight(1f))
                ChipStat("3",  stringResource(R.string.expert_stat_resolved),Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ChipStat(valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                valor,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = Color.White
            )
            Text(
                etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.80f)
            )
        }
    }
}

@Composable
private fun BarraBusqueda() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        readOnly = true,
        placeholder = { Text(stringResource(R.string.expert_search_placeholder), color = PericiaSubtexto) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PericiaSubtexto) },
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor      = PericiaPrimario,
            unfocusedBorderColor    = Color(0xFFDDE3F0),
            unfocusedContainerColor = PericiaSurface,
            focusedContainerColor   = PericiaSurface,
        )
    )
}

@Composable
private fun FiltrosExpediente(filtros: List<String>, seleccionado: Int, onSeleccionar: (Int) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(filtros) { i, filtro ->
            FilterChip(
                selected = seleccionado == i,
                onClick = { onSeleccionar(i) },
                label = { Text(filtro) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PericiaPrimario,
                    selectedLabelColor     = Color.White,
                )
            )
        }
    }
}

@Composable
private fun SeccionExpedientes() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.expert_recent_cases),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PericiaOnFondo
            )
            TextButton(onClick = {}) {
                Text(stringResource(R.string.common_view_all), style = MaterialTheme.typography.labelMedium, color = PericiaAzul)
            }
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            expedientes.forEach { TarjetaExpediente(it) }
        }
    }
}

@Composable
private fun TarjetaExpediente(exp: Expediente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PericiaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(exp.colorEstado)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(exp.numero, style = MaterialTheme.typography.labelSmall, color = PericiaSubtexto)
                        Text(
                            exp.asegurado,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = PericiaOnFondo
                        )
                    }
                    BadgeEstado(exp.etiquetaEstado, exp.colorEstado)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(exp.colorEstado.copy(alpha = 0.10f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(exp.icono, contentDescription = null, tint = exp.colorEstado, modifier = Modifier.size(14.dp))
                    }
                    Text(exp.tipo, style = MaterialTheme.typography.bodySmall, color = PericiaSubtexto)
                    Spacer(Modifier.weight(1f))
                    Text(
                        exp.monto,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = PericiaPrimario
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            stringResource(R.string.expert_case_progress),
                            style = MaterialTheme.typography.labelSmall,
                            color = PericiaSubtexto
                        )
                        Text(
                            "${(exp.progreso * 100).toInt()}%",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = exp.colorEstado
                        )
                    }
                    LinearProgressIndicator(
                        progress = { exp.progreso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = exp.colorEstado,
                        trackColor = exp.colorEstado.copy(alpha = 0.15f),
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Composable
private fun BadgeEstado(etiqueta: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            etiqueta,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = color
        )
    }
}

@Composable
private fun SeccionCitas() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.expert_upcoming_appointments),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PericiaOnFondo
            )
            TextButton(onClick = {}) {
                Text(stringResource(R.string.expert_view_schedule), style = MaterialTheme.typography.labelMedium, color = PericiaAzul)
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(citas) { TarjetaCita(it) }
        }
    }
}

@Composable
private fun TarjetaCita(cita: Cita) {
    val (diaN, mes) = cita.dia.split(" ").let { it[0] to (it.getOrElse(1) { "" }) }
    Card(
        modifier = Modifier.width(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PericiaSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(cita.colorAvatar),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            diaN,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color.White
                        )
                        Text(mes, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.85f))
                    }
                }
                Column {
                    Text(
                        cita.hora,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = PericiaOnFondo
                    )
                    Text(cita.tipoCita, style = MaterialTheme.typography.bodySmall, color = PericiaSubtexto)
                }
            }
            HorizontalDivider(color = Color(0xFFEEF1F8))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(cita.colorAvatar.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = cita.colorAvatar, modifier = Modifier.size(14.dp))
                }
                Text(
                    cita.asegurado,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = PericiaOnFondo
                )
            }
            Text(cita.expedienteId, style = MaterialTheme.typography.labelSmall, color = PericiaSubtexto)
        }
    }
}

@Composable
private fun SeccionDocumentos() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                stringResource(R.string.expert_case_docs),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = PericiaOnFondo
            )
            Text(
                "EXP-2025-0041 · Carlos Mendoza",
                style = MaterialTheme.typography.bodySmall,
                color = PericiaSubtexto
            )
        }
        Spacer(Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PericiaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                documentos.forEachIndexed { i, doc ->
                    FilaDocumento(doc)
                    if (i < documentos.size - 1) HorizontalDivider(color = Color(0xFFEEF1F8))
                }
            }
        }
    }
}

@Composable
private fun FilaDocumento(doc: Documento) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (doc.recibido) PericiaVerde.copy(alpha = 0.12f) else PericiaRojo.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (doc.recibido) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                tint = if (doc.recibido) PericiaVerde else PericiaRojo,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            doc.nombre,
            style = MaterialTheme.typography.bodyMedium,
            color = if (doc.recibido) PericiaOnFondo else PericiaSubtexto,
            modifier = Modifier.weight(1f)
        )
        Text(
            if (doc.recibido) stringResource(R.string.expert_doc_received) else stringResource(R.string.expert_doc_pending),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = if (doc.recibido) PericiaVerde else PericiaAnaranjado
        )
    }
}

@Composable
private fun SeccionEvaluacion() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Text(
            stringResource(R.string.expert_validity_eval),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = PericiaOnFondo,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PericiaSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("EXP-2025-0041", style = MaterialTheme.typography.labelSmall, color = PericiaSubtexto)
                        Text(
                            "Siniestro vehicular",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = PericiaOnFondo
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(PericiaAnaranjado.copy(alpha = 0.12f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            stringResource(R.string.expert_verdict),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = PericiaAnaranjado
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(R.string.expert_validity_index), style = MaterialTheme.typography.bodySmall, color = PericiaSubtexto)
                        Text(
                            "78%",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = PericiaAzul
                        )
                    }
                    LinearProgressIndicator(
                        progress = { 0.78f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        color = PericiaAzul,
                        trackColor = PericiaAzul.copy(alpha = 0.12f),
                        strokeCap = StrokeCap.Round,
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.expert_observations_label),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PericiaOnFondo
                    )
                    listOf(
                        "Daño consistente con el reporte policial.",
                        "Faltan 2 documentos para cierre definitivo.",
                        "Monto estimado dentro del límite de cobertura."
                    ).forEach { obs ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(PericiaAzul)
                            )
                            Text(obs, style = MaterialTheme.typography.bodySmall, color = PericiaSubtexto)
                        }
                    }
                }
            }
        }
    }
}
