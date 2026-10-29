package mx.itson.hogarea.ui.perfil

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.navegacion.irAPestana

fun NavGraphBuilder.perfil(nav: NavController) {
    composable(Rutas.PERFIL) {
        PerfilScreen(
            onIrPestana = nav::irAPestana,
            onEditar = { nav.navigate(Rutas.EDITAR_PERFIL) },
            onSalir = { nav.navigate(Rutas.LOGIN) { popUpTo(nav.graph.id) { inclusive = true } } },
            onPuntos = { nav.navigate(Rutas.PUNTOS) },
        )
    }
    composable(Rutas.EDITAR_PERFIL) { EnConstruccion("10b", "Editar perfil") }
}