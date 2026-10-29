package com.softeen.uishowcase.ui.movra

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softeen.uishowcase.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- Palette (night map + electric lime) ---

private val MovraInk         = Color(0xFF0E1116)
private val MovraSurface     = Color(0xFF171B22)
private val MovraSurfaceHigh = Color(0xFF212731)
private val MovraLime        = Color(0xFFC6F432)
private val MovraOnLime      = Color(0xFF12160A)
private val MovraMuted       = Color(0xFF8B93A1)
private val MovraOutline     = Color(0xFF343B47)
private val MovraStreet      = Color(0xFF2A303B)
private val MovraMinorStreet = Color(0xFF1A1F27)
private val MovraPark        = Color(0xFF15261C)
private val MovraWater       = Color(0xFF10233A)
private val MovraDanger      = Color(0xFFFF7A6B)

private val MovraColors = darkColorScheme(
    primary          = MovraLime,
    onPrimary        = MovraOnLime,
    secondary        = MovraLime,
    onSecondary      = MovraOnLime,
    background       = MovraInk,
    onBackground     = Color.White,
    surface          = MovraSurface,
    onSurface        = Color.White,
    surfaceVariant   = MovraSurfaceHigh,
    onSurfaceVariant = MovraMuted,
    outline          = MovraOutline
)

@Composable
private fun MovraTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = MovraColors, content = content)
}

// --- Fake data ---

private data class RideOption(
    val name: String,
    val seats: Int,
    val etaMinutes: Int,
    val price: String
)

private data class TripRequest(
    val passenger: String,
    val initials: String,
    val rating: String,
    val pickupKm: String,
    val route: String,
    val fare: String,
    val feePercent: Int,
    val fee: String,
    val net: String
)

private const val PICKUP_ADDRESS  = "Av. Paseo de la Reforma 222"
private const val DROPOFF_ADDRESS = "Parque Lincoln, Polanco"
private const val CARD_LABEL      = "•••• 4821"
private const val COUNTDOWN_SECONDS = 15

private val rideOptions = listOf(
    RideOption("Movra Go",      4, 3, "\$89"),
    RideOption("Movra Comfort", 4, 5, "\$124"),
    RideOption("Movra XL",      6, 8, "\$168"),
)

private val incomingRequest = TripRequest(
    passenger  = "Daniela R.",
    initials   = "DR",
    rating     = "4.9",
    pickupKm   = "1.2",
    route      = "Polanco → Aeropuerto T1 · 9.4 km",
    fare       = "\$120.00",
    feePercent = 15,
    fee        = "−\$18.00",
    net        = "\$102.00"
)

private val weeklyEarnings = listOf(820, 1150, 940, 1320, 1680, 1910, 1240)
private const val TODAY_EARNINGS = "\$1,240"
private const val WEEK_EARNINGS  = "\$9,060"
private const val TODAY_TRIPS    = 12

// --- Screen ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovraScreen(navController: NavController) {
    MovraTheme {
        var mode by remember { mutableIntStateOf(0) }
        val scaffoldState = rememberBottomSheetScaffoldState()
        val scope = rememberCoroutineScope()
        val showMessage: (String) -> Unit = { message ->
            scope.launch {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                scaffoldState.snackbarHostState.showSnackbar(message)
            }
        }
        val modeLabels = listOf(
            stringResource(R.string.movra_mode_rider),
            stringResource(R.string.movra_mode_driver)
        )

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 380.dp,
            sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            sheetContainerColor = MovraSurface,
            sheetContentColor = Color.White,
            sheetDragHandle = { BottomSheetDefaults.DragHandle(color = MovraOutline) },
            containerColor = MovraInk,
            snackbarHost = { state ->
                SnackbarHost(state) { data ->
                    Snackbar(
                        snackbarData = data,
                        shape = RoundedCornerShape(14.dp),
                        containerColor = MovraLime,
                        contentColor = MovraOnLime
                    )
                }
            },
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                        .navigationBarsPadding()
                ) {
                    if (mode == 0) {
                        RiderSheet(onMessage = showMessage)
                    } else {
                        DriverSheet(onMessage = showMessage)
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        ) { innerPadding ->
            Box(Modifier.fillMaxSize()) {
                MovraMap(isDriver = mode == 1)

                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = innerPadding.calculateTopPadding())
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MovraSurface)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.common_back),
                                tint = Color.White
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            stringResource(R.string.movra_title),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color.White
                        )
                        Spacer(Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(MovraLime)
                        )
                    }
                    Spacer(Modifier.height(14.dp))
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                        modeLabels.forEachIndexed { index, label ->
                            SegmentedButton(
                                selected = mode == index,
                                onClick = { mode = index },
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = modeLabels.size),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = MovraLime,
                                    activeContentColor = MovraOnLime,
                                    activeBorderColor = MovraLime,
                                    inactiveContainerColor = MovraSurface,
                                    inactiveContentColor = Color.White,
                                    inactiveBorderColor = MovraOutline
                                )
                            ) {
                                Text(label, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- Simulated night map ---

@Composable
private fun MovraMap(isDriver: Boolean) {
    val transition = rememberInfiniteTransition(label = "movraMap")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(7000, easing = LinearEasing), RepeatMode.Restart),
        label = "carProgress"
    )
    val pulse by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Restart),
        label = "pulse"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(MovraInk)
    ) {
        val w = size.width
        val h = size.height

        // Park and river
        drawRoundRect(
            color = MovraPark,
            topLeft = Offset(w * 0.56f, h * 0.30f),
            size = Size(w * 0.30f, h * 0.10f),
            cornerRadius = CornerRadius(12.dp.toPx())
        )
        val river = Path().apply {
            moveTo(-40f, h * 0.50f)
            cubicTo(w * 0.30f, h * 0.44f, w * 0.60f, h * 0.58f, w + 40f, h * 0.48f)
        }
        drawPath(river, MovraWater, style = Stroke(width = 34.dp.toPx(), cap = StrokeCap.Round))

        // Minor street grid
        val step = w / 7f
        var x = step * 0.5f
        while (x < w) {
            drawLine(MovraMinorStreet, Offset(x, 0f), Offset(x, h), strokeWidth = 2.dp.toPx())
            x += step
        }
        var y = step * 0.5f
        while (y < h) {
            drawLine(MovraMinorStreet, Offset(0f, y), Offset(w, y), strokeWidth = 2.dp.toPx())
            y += step
        }

        // Main avenues
        drawLine(MovraStreet, Offset(0f, h * 0.27f), Offset(w, h * 0.21f), strokeWidth = 9.dp.toPx())
        drawLine(MovraStreet, Offset(w * 0.22f, 0f), Offset(w * 0.34f, h), strokeWidth = 9.dp.toPx())
        drawLine(MovraStreet, Offset(w * 0.72f, 0f), Offset(w * 0.64f, h), strokeWidth = 7.dp.toPx())
        drawLine(MovraStreet, Offset(0f, h * 0.40f), Offset(w, h * 0.43f), strokeWidth = 6.dp.toPx())

        if (!isDriver) {
            // Rider: route from pickup to drop-off with an animated car
            val start = Offset(w * 0.18f, h * 0.38f)
            val end = Offset(w * 0.80f, h * 0.16f)
            val route = Path().apply {
                moveTo(start.x, start.y)
                lineTo(w * 0.31f, h * 0.36f)
                cubicTo(w * 0.36f, h * 0.35f, w * 0.37f, h * 0.27f, w * 0.46f, h * 0.25f)
                lineTo(w * 0.66f, h * 0.22f)
                cubicTo(w * 0.73f, h * 0.21f, w * 0.75f, h * 0.18f, end.x, end.y)
            }
            drawPath(route, MovraLime.copy(alpha = 0.18f), style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round))
            drawPath(route, MovraLime, style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round))

            drawCircle(Color.White, radius = 8.dp.toPx(), center = start)
            drawCircle(MovraInk, radius = 3.dp.toPx(), center = start)
            val pin = 14.dp.toPx()
            drawRoundRect(
                color = MovraLime,
                topLeft = Offset(end.x - pin / 2f, end.y - pin / 2f),
                size = Size(pin, pin),
                cornerRadius = CornerRadius(3.dp.toPx())
            )

            val measure = PathMeasure().apply { setPath(route, false) }
            val car = measure.getPosition(measure.length * progress)
            drawCircle(MovraLime.copy(alpha = 0.30f * (1f - pulse)), radius = (10f + 16f * pulse).dp.toPx(), center = car)
            drawCircle(MovraLime, radius = 8.dp.toPx(), center = car)
            drawCircle(MovraInk, radius = 3.dp.toPx(), center = car)
        } else {
            // Driver: demand zones around the driver's position
            val zones = listOf(
                Offset(w * 0.24f, h * 0.20f) to 46f,
                Offset(w * 0.70f, h * 0.14f) to 60f,
                Offset(w * 0.80f, h * 0.34f) to 38f,
                Offset(w * 0.40f, h * 0.36f) to 32f,
            )
            zones.forEach { (center, radius) ->
                drawCircle(MovraLime.copy(alpha = 0.10f), radius = radius.dp.toPx(), center = center)
                drawCircle(MovraLime.copy(alpha = 0.22f), radius = (radius * 0.45f).dp.toPx(), center = center)
            }
            val me = Offset(w * 0.52f, h * 0.25f)
            drawCircle(MovraLime.copy(alpha = 0.35f * (1f - pulse)), radius = (12f + 30f * pulse).dp.toPx(), center = me)
            drawCircle(Color.White, radius = 10.dp.toPx(), center = me)
            drawCircle(MovraLime, radius = 6.dp.toPx(), center = me)
        }
    }
}

// --- Rider mode ---

@Composable
private fun RiderSheet(onMessage: (String) -> Unit) {
    var selectedRide by remember { mutableIntStateOf(0) }
    var payWithCard by remember { mutableStateOf(true) }
    val option = rideOptions[selectedRide]
    val searchingMessage = stringResource(R.string.movra_searching_driver)

    Text(
        stringResource(R.string.movra_where_to),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    )
    Spacer(Modifier.height(14.dp))

    Surface(shape = RoundedCornerShape(18.dp), color = MovraSurfaceHigh) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            RoutePoint(isPickup = true, label = stringResource(R.string.movra_pickup), address = PICKUP_ADDRESS)
            HorizontalDivider(
                modifier = Modifier.padding(start = 26.dp, top = 12.dp, bottom = 12.dp),
                color = MovraOutline
            )
            RoutePoint(isPickup = false, label = stringResource(R.string.movra_dropoff), address = DROPOFF_ADDRESS)
        }
    }

    Spacer(Modifier.height(20.dp))
    Text(
        stringResource(R.string.movra_choose_ride),
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
        color = MovraMuted
    )
    Spacer(Modifier.height(10.dp))
    rideOptions.forEachIndexed { index, ride ->
        RideRow(ride = ride, selected = index == selectedRide, onClick = { selectedRide = index })
        Spacer(Modifier.height(8.dp))
    }

    Spacer(Modifier.height(12.dp))
    Text(
        stringResource(R.string.movra_payment),
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
        color = MovraMuted
    )
    Spacer(Modifier.height(6.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(
            onClick = { payWithCard = !payWithCard },
            label = { Text(if (payWithCard) CARD_LABEL else stringResource(R.string.movra_cash)) },
            leadingIcon = {
                Icon(
                    if (payWithCard) Icons.Default.AccountBox else Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = MovraSurfaceHigh,
                labelColor = Color.White,
                leadingIconContentColor = MovraLime
            ),
            border = BorderStroke(1.dp, MovraOutline)
        )
        AssistChip(
            onClick = {},
            label = { Text(stringResource(R.string.movra_add_promo)) },
            leadingIcon = {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize))
            },
            shape = RoundedCornerShape(12.dp),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color.Transparent,
                labelColor = MovraMuted,
                leadingIconContentColor = MovraMuted
            ),
            border = BorderStroke(1.dp, MovraOutline)
        )
    }

    Spacer(Modifier.height(18.dp))
    Button(
        onClick = { onMessage(searchingMessage) },
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MovraLime, contentColor = MovraOnLime)
    ) {
        Text(stringResource(R.string.movra_request_ride, option.name), fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(8.dp))
        Text("· ${option.price}", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun RoutePoint(isPickup: Boolean, label: String, address: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(if (isPickup) CircleShape else RoundedCornerShape(3.dp))
                .background(if (isPickup) Color.White else MovraLime)
        )
        Spacer(Modifier.width(14.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MovraMuted)
            Text(address, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
        }
    }
}

@Composable
private fun RideRow(ride: RideOption, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (selected) MovraLime.copy(alpha = 0.10f) else Color.Transparent)
            .border(1.dp, if (selected) MovraLime else MovraOutline, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = MovraLime, unselectedColor = MovraMuted)
        )
        CarGlyph(color = if (selected) MovraLime else Color.White)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(ride.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            Text(
                stringResource(R.string.movra_ride_meta, ride.seats, ride.etaMinutes),
                style = MaterialTheme.typography.bodySmall,
                color = MovraMuted
            )
        }
        Text(
            ride.price,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
private fun CarGlyph(color: Color) {
    Canvas(Modifier.size(width = 40.dp, height = 24.dp)) {
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.20f, h * 0.10f),
            size = Size(w * 0.52f, h * 0.40f),
            cornerRadius = CornerRadius(h * 0.20f)
        )
        drawRoundRect(
            color = color,
            topLeft = Offset(0f, h * 0.38f),
            size = Size(w, h * 0.38f),
            cornerRadius = CornerRadius(h * 0.18f)
        )
        listOf(w * 0.24f, w * 0.76f).forEach { cx ->
            drawCircle(MovraSurface, radius = h * 0.17f, center = Offset(cx, h * 0.78f))
            drawCircle(color, radius = h * 0.09f, center = Offset(cx, h * 0.78f))
        }
    }
}

// --- Driver mode ---

@Composable
private fun DriverSheet(onMessage: (String) -> Unit) {
    var online by remember { mutableStateOf(true) }
    var secondsLeft by remember { mutableIntStateOf(COUNTDOWN_SECONDS) }
    val acceptedMessage = stringResource(R.string.movra_trip_accepted)
    val declinedMessage = stringResource(R.string.movra_trip_declined)

    LaunchedEffect(online) {
        if (online) {
            secondsLeft = COUNTDOWN_SECONDS
            while (true) {
                delay(1000)
                secondsLeft = if (secondsLeft > 1) secondsLeft - 1 else COUNTDOWN_SECONDS
            }
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(
                stringResource(if (online) R.string.movra_online else R.string.movra_offline),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                stringResource(if (online) R.string.movra_online_hint else R.string.movra_offline_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MovraMuted
            )
        }
        Switch(
            checked = online,
            onCheckedChange = { online = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MovraOnLime,
                checkedTrackColor = MovraLime,
                uncheckedThumbColor = MovraMuted,
                uncheckedTrackColor = MovraSurfaceHigh,
                uncheckedBorderColor = MovraOutline
            )
        )
    }

    Spacer(Modifier.height(16.dp))
    if (online) {
        RequestCard(
            request = incomingRequest,
            secondsLeft = secondsLeft,
            onAccept = {
                secondsLeft = COUNTDOWN_SECONDS
                onMessage(acceptedMessage)
            },
            onDecline = {
                secondsLeft = COUNTDOWN_SECONDS
                onMessage(declinedMessage)
            }
        )
        Spacer(Modifier.height(16.dp))
    }

    EarningsCard()
}

@Composable
private fun RequestCard(
    request: TripRequest,
    secondsLeft: Int,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MovraSurfaceHigh,
        border = BorderStroke(1.dp, MovraLime.copy(alpha = 0.55f))
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(
                        stringResource(R.string.movra_new_request),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MovraLime
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MovraOutline),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(request.initials, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(request.passenger, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = MovraLime, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(request.rating, style = MaterialTheme.typography.bodySmall, color = MovraMuted)
                            }
                        }
                    }
                }
                CountdownRing(secondsLeft = secondsLeft)
            }

            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MovraMuted, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(
                    stringResource(R.string.movra_pickup_distance, request.pickupKm),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MovraMuted
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(request.route, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))

            Spacer(Modifier.height(14.dp))
            FareRow(stringResource(R.string.movra_fare_trip), request.fare, Color.White)
            FareRow(stringResource(R.string.movra_fare_fee, request.feePercent), request.fee, MovraDanger)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MovraOutline)
            FareRow(stringResource(R.string.movra_fare_net), request.net, MovraLime, emphasized = true)

            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onDecline,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, MovraOutline),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text(stringResource(R.string.movra_decline))
                }
                Button(
                    onClick = onAccept,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MovraLime, contentColor = MovraOnLime)
                ) {
                    Text(stringResource(R.string.movra_accept), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CountdownRing(secondsLeft: Int) {
    val fraction by animateFloatAsState(
        targetValue = secondsLeft / COUNTDOWN_SECONDS.toFloat(),
        label = "countdown"
    )
    Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = 5.dp.toPx()
            val inset = stroke / 2f
            val arcSize = Size(size.width - stroke, size.height - stroke)
            drawArc(
                color = MovraOutline,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = stroke)
            )
            drawArc(
                color = MovraLime,
                startAngle = -90f,
                sweepAngle = 360f * fraction,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Text(
            secondsLeft.toString(),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun FareRow(label: String, amount: String, amountColor: Color, emphasized: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            modifier = Modifier.weight(1f),
            style = if (emphasized) {
                MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            } else {
                MaterialTheme.typography.bodyMedium
            },
            color = if (emphasized) Color.White else MovraMuted
        )
        Text(
            amount,
            style = if (emphasized) {
                MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            } else {
                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            },
            color = amountColor
        )
    }
}

@Composable
private fun EarningsCard() {
    val days = stringResource(R.string.movra_week_days).split(",")
    Surface(shape = RoundedCornerShape(20.dp), color = MovraSurfaceHigh) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(stringResource(R.string.movra_earnings_today), style = MaterialTheme.typography.labelMedium, color = MovraMuted)
                    Text(TODAY_EARNINGS, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold))
                    Text(
                        stringResource(R.string.movra_trips_count, TODAY_TRIPS),
                        style = MaterialTheme.typography.bodySmall,
                        color = MovraMuted
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(stringResource(R.string.movra_this_week), style = MaterialTheme.typography.labelMedium, color = MovraMuted)
                    Text(WEEK_EARNINGS, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MovraLime)
                }
            }

            Spacer(Modifier.height(16.dp))
            Canvas(Modifier.fillMaxWidth().height(96.dp)) {
                val max = weeklyEarnings.max().toFloat()
                val slot = size.width / weeklyEarnings.size
                val barWidth = slot * 0.46f
                weeklyEarnings.forEachIndexed { index, value ->
                    val barHeight = size.height * (value / max)
                    drawRoundRect(
                        color = if (index == weeklyEarnings.lastIndex) MovraLime else MovraLime.copy(alpha = 0.28f),
                        topLeft = Offset(slot * index + (slot - barWidth) / 2f, size.height - barHeight),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(barWidth / 2f)
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth()) {
                days.forEachIndexed { index, day ->
                    Text(
                        day,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (index == days.lastIndex) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (index == days.lastIndex) MovraLime else MovraMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
