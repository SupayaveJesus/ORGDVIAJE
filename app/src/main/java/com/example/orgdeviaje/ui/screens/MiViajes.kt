package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.orgdeviaje.data.model.Viaje
import com.example.orgdeviaje.ui.nav.NavScreens
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.ViajeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisViajesScreen(
    username: String,
    navController: NavController,
    viajeViewModel: ViajeViewModel = viewModel()
) {
    val viajes = viajeViewModel.listViajes

    // Cargar los viajes del usuario logueado de la APi
    LaunchedEffect(username) {
        viajeViewModel.getTripsByUsername(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tus viajes: $username",
                        fontWeight = FontWeight.Bold
                    )
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { navController.popBackStack() }) {
                        Text("Volver")
                    }
                    Button(
                        onClick = {
                            navController.navigate(
                                "${NavScreens.TRIP_DETAIL.name}/0/Nuevo Viaje/$username"
                            )
                        }
                    ) {
                        Text("Crear Viaje")
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
                    text = "No tienes viajes aún, crea uno nuevo",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxSize()
                ) {
                    items(viajes) { viaje ->
                        ViajeCard(viaje = viaje, navController = navController, username = username)
                    }
                }
            }
        }
    }
}

@Composable
fun ViajeCard(viaje: Viaje, navController: NavController, username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = {
            navController.navigate("${NavScreens.TRIP_DETAIL.name}/${viaje.id}/${viaje.nombre}/$username")
        }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = viaje.nombre ?: "(Sin nombre)",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = viaje.pais ?: "(Sin país)",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${viaje.usuario ?: "(Desconocido)"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MisViajesPreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        MisViajesScreen(username = "pepito", navController = navController)
    }
}
