package mx.itson.hogarea.ui.estadisticas

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.navegacion.irAPestana


fun NavGraphBuilder.estadisticas(nav: NavController) {
    composable(Rutas.ESTADISTICAS) { EstadisticasScreen(onIrPestana = nav::irAPestana) }
}