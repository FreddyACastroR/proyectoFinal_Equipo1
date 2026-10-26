package mx.itson.hogarea.ui.detalle

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.detalle(nav: NavController) {
    val conId = listOf(navArgument("id") { type = NavType.IntType })
    composable(Rutas.DETALLE, arguments = conId) { EnConstruccion("07", "Detalle de tarea") }
    composable(Rutas.DETALLE_LECTURA, arguments = conId) { EnConstruccion("07d", "Detalle (solo lectura)") }
}