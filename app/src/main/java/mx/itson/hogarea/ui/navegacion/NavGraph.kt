package mx.itson.hogarea.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import mx.itson.hogarea.ui.acceso.acceso
import mx.itson.hogarea.ui.configuracion.configuracion
import mx.itson.hogarea.ui.detalle.detalle
import mx.itson.hogarea.ui.estadisticas.estadisticas
import mx.itson.hogarea.ui.extras.extras
import mx.itson.hogarea.ui.hogar.hogar
import mx.itson.hogarea.ui.inicio.inicio
import mx.itson.hogarea.ui.perfil.perfil
import mx.itson.hogarea.ui.tareas.tareas

@Composable
fun NavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Rutas.LOGIN) {
        acceso(nav)
        hogar(nav)
        inicio(nav)
        tareas(nav)
        detalle(nav)
        configuracion(nav)
        estadisticas(nav)
        perfil(nav)
        extras(nav)
    }
}

fun NavController.irAPestana(ruta: String) {
    navigate(ruta) {
        popUpTo(Rutas.MIS_TAREAS)
        launchSingleTop = true
    }
}