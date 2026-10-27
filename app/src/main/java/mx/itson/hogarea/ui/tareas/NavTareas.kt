package mx.itson.hogarea.ui.tareas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    composable(Rutas.EDITAR_TAREA) {
        NuevaTareaScreen(
            editar = true,
            onCancelar = { nav.popBackStack() },
            onSiguiente = { nav.navigate(Rutas.EDITAR_ASIGNACION) },
        )
    }
    composable(Rutas.EDITAR_ASIGNACION) {
        AsignarTareaScreen(
            editar = true,
            onAtras = { nav.popBackStack() },
            onGuardar = { nav.popBackStack(Rutas.DETALLE, inclusive = false) },
        )
    }
}