package mx.itson.hogarea.ui.tareas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.tareas(nav: NavController) {
    composable(Rutas.NUEVA_TAREA) { EnConstruccion("06a", "Nueva tarea") }
    composable(Rutas.ASIGNAR_TAREA) { EnConstruccion("06b", "Asignar tarea") }
    composable(Rutas.EDITAR_TAREA) { EnConstruccion("06c", "Editar tarea") }
    composable(Rutas.EDITAR_ASIGNACION) { EnConstruccion("06d", "Editar asignación") }
}