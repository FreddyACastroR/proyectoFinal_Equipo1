package mx.itson.hogarea.ui.tareas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.itson.hogarea.ui.navegacion.Rutas


fun NavGraphBuilder.tareas(nav: NavController) {
    val conId = listOf(navArgument("id") { type = NavType.IntType })
    composable(Rutas.NUEVA_TAREA) {
        NuevaTareaScreen(
            id = null,
            onCancelar = { nav.popBackStack() },
            onSiguiente = { nav.navigate(Rutas.ASIGNAR_TAREA) },
        )
    }
    composable(Rutas.ASIGNAR_TAREA) {
        AsignarTareaScreen(
            id = null,
            onAtras = { nav.popBackStack() },
            onGuardar = { nav.popBackStack(Rutas.MIS_TAREAS, inclusive = false) },
        )
    }
    composable(Rutas.EDITAR_TAREA, arguments = conId) { entrada ->
        val id = entrada.arguments?.getInt("id") ?: 1
        NuevaTareaScreen(
            id = id,
            onCancelar = { nav.popBackStack() },
            onSiguiente = { nav.navigate(Rutas.editarAsignacion(id)) },
        )
    }
    composable(Rutas.EDITAR_ASIGNACION, arguments = conId) { entrada ->
        AsignarTareaScreen(
            id = entrada.arguments?.getInt("id") ?: 1,
            onAtras = { nav.popBackStack() },
            onGuardar = { nav.popBackStack(Rutas.DETALLE, inclusive = false) },
        )
    }
}