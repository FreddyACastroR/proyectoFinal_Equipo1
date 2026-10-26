package mx.itson.hogarea.ui.extras

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.extras(nav: NavController) {
    composable(Rutas.HISTORIAL) { EnConstruccion("11", "Historial") }
    composable(Rutas.PUNTOS) { EnConstruccion("12", "Puntos y recompensas") }
    composable(Rutas.NOTIFICACIONES) { EnConstruccion("13", "Notificaciones") }
}