package mx.itson.hogarea.ui.extras

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.extras(nav: NavController) {
    composable(Rutas.NOTIFICACIONES) { NotificacionesScreen(onAtras = { nav.popBackStack() }) }
    composable(Rutas.HISTORIAL) { HistorialScreen(onAtras = { nav.popBackStack() }) }
    composable(Rutas.PUNTOS) {
        PuntosScreen(
            onAtras = { nav.popBackStack() },
            onHistorial = { nav.navigate(Rutas.HISTORIAL) },
        )
    }
}