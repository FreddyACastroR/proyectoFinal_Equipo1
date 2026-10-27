package mx.itson.hogarea.ui.tareas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

// 06a a 06e Rubén (ruben/crear-editar-tarea)
fun NavGraphBuilder.tareas(nav: NavController) {
    composable(Rutas.NUEVA_TAREA) {
        NuevaTareaScreen(
            editar = false,
            onCancelar = { nav.popBackStack() },
            onSiguiente = { nav.navigate(Rutas.ASIGNAR_TAREA) },
        )
    }
    composable(Rutas.ASIGNAR_TAREA) {
        AsignarTareaScreen(
            editar = false,
            onAtras = { nav.popBackStack() },
            onGuardar = { nav.popBackStack(Rutas.MIS_TAREAS, inclusive = false) },
        )
    }
    composable(Rutas.EDITAR_TAREA) { EnConstruccion("06c", "Editar tarea") }
    composable(Rutas.EDITAR_ASIGNACION) { EnConstruccion("06d", "Editar asignación") }
}