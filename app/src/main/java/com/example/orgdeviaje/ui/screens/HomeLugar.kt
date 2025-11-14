package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.orgdeviaje.ui.nav.NavScreens
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.ViajeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    viajeViewModel: ViajeViewModel = viewModel()
) {
    val viajes = viajeViewModel.listViajes

    // Carga los viajes de la API al entrar
    LaunchedEffect(Unit) {
        viajeViewModel.getAllTrips()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Viajes Registrados", fontWeight = FontWeight.Bold)
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 3.dp,
                shadowElevation = 6.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            navController.navigate("${NavScreens.MY_TRIPS.name}/$username")
                        }
                    ) {
                        Text("Mis viajes")
                    }

                    Button(
                        onClick = {
                            navController.navigate("${NavScreens.ADD_TRIP.name}/$username")
                        }
                    ) {
                        Text("Nuevo Viaje")
                    }
                }
            }
        }

    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (viajes.isEmpty()) {
                Text(
                    text = "No hay viajes registrados aún.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxSize()
                ) {
                    items(viajes) { viaje ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            onClick = {
                                navController.navigate(
                                    "${NavScreens.TRIP_DETAIL.name}/${viaje.id}/${viaje.nombre}/$username"
                                )
                            }

                        ) {
                            Column(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = viaje.nombre ?: "(Sin nombre)",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = viaje.pais ?: "(Sin país)",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = " ${viaje.usuario ?: "(Desconocido)"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController, username = "pepito")
    }
}
