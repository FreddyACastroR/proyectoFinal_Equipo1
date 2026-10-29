package mx.itson.hogarea.ui.inicio

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.navegacion.irAPestana

fun NavGraphBuilder.inicio(nav: NavController) {
    composable(Rutas.MIS_TAREAS) {
        MisTareasScreen(
            soloLectura = false,
            onIrPestana = nav::irAPestana,
            onNotificaciones = { nav.navigate(Rutas.NOTIFICACIONES) },
            onNuevaTarea = { nav.navigate(Rutas.NUEVA_TAREA) },
            onAbrirTarea = { id -> nav.navigate(Rutas.detalle(id)) },
        )
    }
    composable(Rutas.TAREAS_HOGAR) {
        MisTareasScreen(
            soloLectura = true,
            onIrPestana = nav::irAPestana,
            onNotificaciones = { nav.navigate(Rutas.NOTIFICACIONES) },
            onNuevaTarea = { },
            onAbrirTarea = { id -> nav.navigate(Rutas.detalleLectura(id)) },
        )
    }
}