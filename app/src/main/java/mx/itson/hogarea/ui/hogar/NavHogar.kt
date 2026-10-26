package mx.itson.hogarea.ui.hogar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.hogar(nav: NavController) {
    composable(Rutas.BIENVENIDA) { EnConstruccion("04", "Crear o unirse a un hogar") }
    composable(Rutas.CREAR_HOGAR) { EnConstruccion("04b", "Crear hogar") }
    composable(Rutas.HOGAR_CREADO) { EnConstruccion("04c", "Hogar creado") }
    composable(Rutas.UNIRSE) { EnConstruccion("04d", "Unirme con código") }
}