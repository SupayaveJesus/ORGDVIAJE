package com.example.orgdeviaje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.orgdeviaje.ui.screens.*
import com.example.orgdeviaje.ui.theme.ORGDEViajeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ORGDEViajeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // Splash
        composable("splash") {
            SplashLoginScreen(navController = navController)
        }

        //Home (todos los viajes)
        composable(
            route = "home/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HomeScreen(navController = navController, username = username)
        }

        //Mis viajes (corregido)
        composable(
            route = "mytrips/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MisViajesScreen(
                userName = username,
                navController = navController
            )
        }

        //Crear un viaje nuevo
        composable(
            route = "addTrip/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            AddTripScreen(
                userName = username,
                navController = navController
            )
        }

        // Detalle del viaje (corregido)
        composable(
            route = "tripDetail/{viajeId}/{viajeNombre}/{userName}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viajeId = backStackEntry.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStackEntry.arguments?.getString("viajeNombre") ?: ""
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            TripDetalleScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                userName = userName,
                navController = navController
            )
        }

        //Agregar lugar
        composable(
            route = "addPlace/{viajeId}/{viajeNombre}/{userName}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viajeId = backStackEntry.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStackEntry.arguments?.getString("viajeNombre") ?: ""
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            AddPlaceScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                userName = userName,
                navController = navController
            )
        }

        //Detalle del lugar
        composable(
            route = "placeDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            PlaceDetailScreen(
                lugarId = placeId,
                navController = navController
            )
        }
    }
}
