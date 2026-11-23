package mx.itson.hogarea.ui.detalle

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.detalle(nav: NavController) {
    val conId = listOf(navArgument("id") { type = NavType.IntType })
    composable(Rutas.DETALLE, arguments = conId) { entrada ->
        val id = entrada.arguments?.getInt("id") ?: 1
        DetalleTarea(
            id = id,
            puedeEditar = true,
            onAtras = { nav.popBackStack() },
            onEditar = { nav.navigate(Rutas.editarTarea(id)) },
        )
    }
    composable(Rutas.DETALLE_LECTURA, arguments = conId) { entrada ->
        DetalleTarea(
            id = entrada.arguments?.getInt("id") ?: 2,
            puedeEditar = false,
            onAtras = { nav.popBackStack() },
            onEditar = { },
        )
    }
}