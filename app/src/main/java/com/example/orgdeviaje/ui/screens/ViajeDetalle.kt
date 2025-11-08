package com.example.orgdeviaje.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.orgdeviaje.data.model.Lugar
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme
import com.example.orgdeviaje.viewmodels.LugarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetalleScreen(
    viajeId: Int,
    viajeNombre: String,
    userName: String,
    navController: NavController,
    viewModel: LugarViewModel = viewModel()
) {
    val lugares = viewModel.listLugares

    // Cargar los lugares del viaje desde la API
    LaunchedEffect(viajeId) {
        viewModel.getPlacesByTrip(viajeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lugares de $viajeNombre") }
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
                    OutlinedButton(onClick = { navController.popBackStack() }) {
                        Text("Volver")
                    }
                    Button(onClick = {
                        navController.navigate("addPlace/$viajeId/$viajeNombre/$userName")
                    }) {
                        Text("Agregar Lugar")
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (lugares.isEmpty()) {
                Text(
                    text = "Aún no hay lugares agregados a este viaje 🏝️",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxSize()
                ) {
                    items(lugares) { lugar ->
                        LugarCard(lugar = lugar)
                    }
                }
            }
        }
    }
}

@Composable
fun LugarCard(lugar: Lugar) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            if (!lugar.imagenUrl.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(lugar.imagenUrl),
                    contentDescription = lugar.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = lugar.nombre ?: "(Sin nombre)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = lugar.ciudad ?: "(Ciudad desconocida)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (!lugar.descripcion.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lugar.descripcion,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TripDetallePreview() {
    ORGDEViajeTheme {
        val navController = rememberNavController()
        TripDetalleScreen(
            viajeId = 1,
            viajeNombre = "Viaje a Disney",
            userName = "pepito",
            navController = navController
        )
    }
}
