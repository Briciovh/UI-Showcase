package com.example.uishowcase.ui.medicare

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Private palette
private val MediCareBg          = Color(0xFFF8FFFE)
private val MediCareTealPrimary = Color(0xFF26A69A)
private val MediCareOnBg        = Color(0xFF1A2C2B)
private val MediCareSubtext     = Color(0xFF607D8B)

@Composable
private fun MediCareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MediCareTealPrimary,
            background = MediCareBg,
            surface = Color.White,
            onBackground = MediCareOnBg,
            onSurface = MediCareOnBg,
            onPrimary = Color.White,
        ),
        content = content
    )
}

// Fake data
private val specialties = listOf("All", "Cardiologist", "Neurologist", "Pediatrician", "Orthopedic", "Dermatologist")

private data class Doctor(
    val name: String,
    val specialty: String,
    val rating: Float,
    val experience: String,
    val avatarColor: Color
)

private val doctors = listOf(
    Doctor("Dr. Sarah Chen",   "Cardiologist", 4.9f, "12 yrs", Color(0xFF26A69A)),
    Doctor("Dr. James Miller", "Neurologist",  4.8f, "15 yrs", Color(0xFF5C6BC0)),
    Doctor("Dr. Aisha Patel",  "Pediatrician", 4.7f, "8 yrs",  Color(0xFFEC407A)),
    Doctor("Dr. Tom Nguyen",   "Orthopedic",   4.6f, "10 yrs", Color(0xFF42A5F5)),
)

// Triple: (dayLabel, dateNumber, preSelected)
private val dateSlots = listOf(
    Triple("Mon", "12", false),
    Triple("Tue", "13", false),
    Triple("Wed", "14", true),
    Triple("Thu", "15", false),
    Triple("Fri", "16", false),
)

private val timeSlots = listOf("9:00 AM", "10:30 AM", "12:00 PM", "2:00 PM", "4:30 PM")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediCareScreen(navController: NavController) {
    var selectedSpecialty by remember { mutableStateOf(0) }
    var selectedDate by remember { mutableStateOf(2) }
    var selectedTime by remember { mutableStateOf(0) }

    MediCareTheme {
        Scaffold(
            containerColor = MediCareBg,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MediCareOnBg
                            )
                        }
                    },
                    title = {
                        Column {
                            Text(
                                "Good morning,",
                                style = MaterialTheme.typography.bodySmall,
                                color = MediCareSubtext
                            )
                            Text(
                                "Alex",
                                style = MaterialTheme.typography.titleMedium,
                                color = MediCareOnBg
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = MediCareTealPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MediCareBg)
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
                        placeholder = {
                            Text("Search doctors, specialists...", color = MediCareSubtext)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MediCareSubtext)
                        },
                        shape = RoundedCornerShape(28.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MediCareTealPrimary,
                            unfocusedBorderColor = MediCareTealPrimary.copy(alpha = 0.5f),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Specialties
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        itemsIndexed(specialties) { index, specialty ->
                            FilterChip(
                                selected = selectedSpecialty == index,
                                onClick = { selectedSpecialty = index },
                                label = { Text(specialty) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MediCareTealPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Doctors section label
                item {
                    Text(
                        "Available Doctors",
                        style = MaterialTheme.typography.titleMedium,
                        color = MediCareOnBg,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Doctors row
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        items(doctors) { doctor ->
                            DoctorCard(doctor)
                        }
                    }
                }

                // Date section label
                item {
                    Text(
                        "Select Date",
                        style = MaterialTheme.typography.titleMedium,
                        color = MediCareOnBg,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Date selector
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        itemsIndexed(dateSlots) { index, slot ->
                            DateCard(
                                day = slot.first,
                                date = slot.second,
                                isSelected = selectedDate == index,
                                onClick = { selectedDate = index }
                            )
                        }
                    }
                }

                // Time section label
                item {
                    Text(
                        "Select Time",
                        style = MaterialTheme.typography.titleMedium,
                        color = MediCareOnBg,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Time slots
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        itemsIndexed(timeSlots) { index, time ->
                            FilterChip(
                                selected = selectedTime == index,
                                onClick = { selectedTime = index },
                                label = { Text(time) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MediCareTealPrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }

                // Summary card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MediCareTealPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Appointment Summary",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            SummaryRow("Doctor", "Dr. Sarah Chen")
                            SummaryRow(
                                "Date",
                                "${dateSlots[selectedDate].first}, ${dateSlots[selectedDate].second} Apr"
                            )
                            SummaryRow("Time", timeSlots[selectedTime])
                            Spacer(Modifier.height(4.dp))
                            Button(
                                onClick = {},
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = MediCareTealPrimary
                                )
                            ) {
                                Text("Confirm Booking", style = MaterialTheme.typography.labelLarge)
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
private fun DoctorCard(doctor: Doctor) {
    val initials = doctor.name
        .split(" ")
        .filter { it != "Dr." }
        .take(2)
        .joinToString("") { it.first().toString() }

    Card(
        modifier = Modifier.width(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(doctor.avatarColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(initials, style = MaterialTheme.typography.titleMedium, color = Color.White)
            }
            Text(
                doctor.name,
                style = MaterialTheme.typography.bodySmall,
                color = MediCareOnBg,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(doctor.specialty, style = MaterialTheme.typography.labelSmall, color = MediCareSubtext)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(12.dp)
                )
                Text(doctor.rating.toString(), style = MaterialTheme.typography.labelSmall, color = MediCareSubtext)
                Text("• ${doctor.experience}", style = MaterialTheme.typography.labelSmall, color = MediCareSubtext)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateCard(day: String, date: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MediCareTealPrimary else Color.White
        ),
        border = if (isSelected) null else BorderStroke(1.dp, MediCareTealPrimary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                day,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) Color.White else MediCareTealPrimary
            )
            Text(
                date,
                style = MaterialTheme.typography.titleSmall,
                color = if (isSelected) Color.White else MediCareOnBg
            )
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
        Text(value, style = MaterialTheme.typography.bodySmall, color = Color.White)
    }
}
