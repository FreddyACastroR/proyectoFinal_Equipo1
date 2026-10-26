package mx.itson.hogarea.ui.inicio

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.inicio(nav: NavController) {
    composable(Rutas.MIS_TAREAS) { EnConstruccion("05", "Mis tareas") }
    composable(Rutas.TAREAS_HOGAR) { EnConstruccion("05b", "Tareas de un hogar") }
}