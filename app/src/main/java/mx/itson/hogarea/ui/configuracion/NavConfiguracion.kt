package mx.itson.hogarea.ui.configuracion

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.navegacion.irAPestana

fun NavGraphBuilder.configuracion(nav: NavController) {
    composable(Rutas.CONFIG_HOGAR) {
        ConfiguracionHogarScreen(
            onIrPestana = nav::irAPestana,
            onMiembros = { nav.navigate(Rutas.MIEMBROS) },
            onHabitaciones = { nav.navigate(Rutas.HABITACIONES) },
            onVerComoMiembro = { nav.navigate(Rutas.HOGAR_MIEMBRO) },
        )
    }
    composable(Rutas.HABITACIONES) {
        HabitacionesScreen(
            onAtras = { nav.popBackStack() },
            onEditar = { nav.navigate(Rutas.EDITAR_HABITACION) },
            onAgregar = { nav.navigate(Rutas.AGREGAR_HABITACION) },
        )
    }
    composable(Rutas.MIEMBROS) {
        MiembrosScreen(onAtras = { nav.popBackStack() }, onMiembro = { nav.navigate(Rutas.MIEMBRO) })
    }
    composable(Rutas.MIEMBRO) { MiembroScreen(onAtras = { nav.popBackStack() }) }
    composable(Rutas.EDITAR_HABITACION) { HabitacionFormScreen(editar = true, onAtras = { nav.popBackStack() }) }
    composable(Rutas.AGREGAR_HABITACION) { HabitacionFormScreen(editar = false, onAtras = { nav.popBackStack() }) }
    composable(Rutas.HOGAR_MIEMBRO) {
        HogarMiembroScreen(
            onIrPestana = nav::irAPestana,
            onVerComoCreador = { nav.popBackStack() },
            onSalir = { nav.popBackStack() },
        )
    }
}