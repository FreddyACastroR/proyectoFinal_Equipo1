package mx.itson.hogarea.ui.configuracion

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.componentes.EnConstruccion
import mx.itson.hogarea.ui.navegacion.Rutas

fun NavGraphBuilder.configuracion(nav: NavController) {
    composable(Rutas.CONFIG_HOGAR) { EnConstruccion("08", "Configuración del hogar") }
    composable(Rutas.HABITACIONES) { EnConstruccion("08b", "Habitaciones") }
    composable(Rutas.MIEMBROS) { EnConstruccion("08d", "Miembros del hogar") }
    composable(Rutas.MIEMBRO) { EnConstruccion("08e", "Detalle de miembro") }
    composable(Rutas.EDITAR_HABITACION) { EnConstruccion("08f", "Editar habitación") }
    composable(Rutas.AGREGAR_HABITACION) { EnConstruccion("08g", "Agregar habitación") }
    composable(Rutas.HOGAR_MIEMBRO) { EnConstruccion("08i", "Hogar (vista de miembro)") }
}