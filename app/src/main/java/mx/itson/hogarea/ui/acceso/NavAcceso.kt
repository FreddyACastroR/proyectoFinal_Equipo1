package mx.itson.hogarea.ui.acceso

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.acceso(nav: NavController) {
    composable(Rutas.LOGIN) { EnConstruccion("01", "Iniciar sesión") }
    composable(Rutas.RECUPERAR) { EnConstruccion("02", "Recuperar contraseña") }
    composable(Rutas.REGISTRO) { EnConstruccion("03", "Registro") }
}