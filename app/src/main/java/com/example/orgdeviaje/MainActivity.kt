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
import com.example.orgdeviaje.ui.nav.NavScreens
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
        startDestination = NavScreens.SPLASH.name
    ) {
        // 🟢 Splash / Login
        composable(NavScreens.SPLASH.name) {
            SplashLoginScreen(navController = navController)
        }

        // 🟡 Home (todos los viajes)
        composable(
            route = "${NavScreens.HOME.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            HomeScreen(navController = navController, username = username)
        }

        // 🟣 Mis viajes
        composable(
            route = "${NavScreens.MY_TRIPS.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            MisViajesScreen(
                userName = username,
                navController = navController
            )
        }

        // 🟢 Crear viaje
        composable(
            route = "${NavScreens.ADD_TRIP.name}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStack ->
            val username = backStack.arguments?.getString("username") ?: ""
            AddTripScreen(
                userName = username,
                navController = navController
            )
        }

        // 🔵 Detalle del viaje
        composable(
            route = "${NavScreens.TRIP_DETAIL.name}/{viajeId}/{viajeNombre}/{userName}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStack ->
            val viajeId = backStack.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStack.arguments?.getString("viajeNombre") ?: ""
            val userName = backStack.arguments?.getString("userName") ?: ""
            TripDetalleScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                userName = userName,
                navController = navController
            )
        }

        // 🟠 Agregar lugar
        composable(
            route = "${NavScreens.ADD_PLACE.name}/{viajeId}/{viajeNombre}/{userName}",
            arguments = listOf(
                navArgument("viajeId") { type = NavType.IntType },
                navArgument("viajeNombre") { type = NavType.StringType },
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStack ->
            val viajeId = backStack.arguments?.getInt("viajeId") ?: 0
            val viajeNombre = backStack.arguments?.getString("viajeNombre") ?: ""
            val userName = backStack.arguments?.getString("userName") ?: ""
            AddPlaceScreen(
                viajeId = viajeId,
                viajeNombre = viajeNombre,
                userName = userName,
                navController = navController
            )
        }

        // 🔴 Detalle del lugar
        composable(
            route = "${NavScreens.PLACE_DETAIL.name}/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStack ->
            val placeId = backStack.arguments?.getInt("placeId") ?: 0
            PlaceDetailScreen(
                lugarId = placeId,
                navController = navController
            )
        }
    }
}
