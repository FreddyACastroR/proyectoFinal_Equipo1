package mx.itson.hogarea.ui.hogar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.hogar(nav: NavController) {
    composable(Rutas.BIENVENIDA) {
        BienvenidaScreen(
            onCrear = { nav.navigate(Rutas.CREAR_HOGAR) },
            onUnirse = { nav.navigate(Rutas.UNIRSE) },
        )
    }
    composable(Rutas.UNIRSE) {
        UnirseScreen(
            onAtras = { nav.popBackStack() },
            // Al unirse se limpia la pila: "atrás" ya no regresa al registro
            onUnirme = { nav.navigate(Rutas.TAREAS_HOGAR) { popUpTo(nav.graph.id) { inclusive = true } } },
        )
    }
    composable(Rutas.CREAR_HOGAR) { EnConstruccion("04b", "Crear hogar") }
    composable(Rutas.HOGAR_CREADO) { EnConstruccion("04c", "Hogar creado") }
}