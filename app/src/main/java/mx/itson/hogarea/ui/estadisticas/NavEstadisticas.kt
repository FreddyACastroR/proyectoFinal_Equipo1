package mx.itson.hogarea.ui.estadisticas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.estadisticas(nav: NavController) {
    composable(Rutas.ESTADISTICAS) { EnConstruccion("09", "Estadísticas") }
}