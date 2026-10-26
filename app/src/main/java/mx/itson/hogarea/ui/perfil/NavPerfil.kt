package mx.itson.hogarea.ui.perfil

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.perfil(nav: NavController) {
    composable(Rutas.PERFIL) { EnConstruccion("10", "Perfil") }
    composable(Rutas.EDITAR_PERFIL) { EnConstruccion("10b", "Editar perfil") }
}