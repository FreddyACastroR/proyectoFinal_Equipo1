package mx.itson.hogarea.ui.acceso

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.acceso(nav: NavController) {
    composable(Rutas.LOGIN) {
        LoginScreen(
            onEntrar = { nav.navigate(Rutas.MIS_TAREAS) { popUpTo(Rutas.LOGIN) { inclusive = true } } },
            onOlvide = { nav.navigate(Rutas.RECUPERAR) },
            onRegistro = { nav.navigate(Rutas.REGISTRO) },
        )
    }
    composable(Rutas.RECUPERAR) { RecuperarScreen(onAtras = { nav.popBackStack() }) }
    composable(Rutas.REGISTRO) { EnConstruccion("03", "Registro") }
}