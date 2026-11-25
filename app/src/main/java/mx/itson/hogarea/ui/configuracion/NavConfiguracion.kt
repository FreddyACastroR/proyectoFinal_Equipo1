package mx.itson.hogarea.ui.configuracion

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.esAdmin
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.navegacion.irAPestana

fun NavGraphBuilder.configuracion(nav: NavController) {
    composable(Rutas.CONFIG_HOGAR) {
        if (esAdmin(Sesion.hogarActivo)) {
            ConfiguracionHogarScreen(
                onIrPestana = nav::irAPestana,
                onMiembros = { nav.navigate(Rutas.MIEMBROS) },
                onMiembro = { persona -> nav.navigate(Rutas.miembro(persona.name)) },
                onHabitaciones = { nav.navigate(Rutas.HABITACIONES) },
            )
        } else {
            HogarMiembroScreen(
                onIrPestana = nav::irAPestana,
                onSalir = { nav.irAPestana(Rutas.MIS_TAREAS) },
            )
        }
    }
    composable(Rutas.HABITACIONES) {
        HabitacionesScreen(
            onAtras = { nav.popBackStack() },
            onEditar = { indice, agregarTarea -> nav.navigate(Rutas.editarHabitacion(indice) + "?tarea=$agregarTarea") },
            onAgregar = { nav.navigate(Rutas.AGREGAR_HABITACION) },
        )
    }
    composable(Rutas.MIEMBROS) {
        MiembrosScreen(
            onAtras = { nav.popBackStack() },
            onMiembro = { persona -> nav.navigate(Rutas.miembro(persona.name)) },
        )
    }
    composable(Rutas.MIEMBRO, arguments = listOf(navArgument("persona") { type = NavType.StringType })) { entrada ->
        val persona = Persona.valueOf(entrada.arguments?.getString("persona") ?: Persona.FREDDY.name)
        MiembroScreen(persona = persona, onAtras = { nav.popBackStack() })
    }
    composable(
        Rutas.EDITAR_HABITACION + "?tarea={tarea}",
        arguments = listOf(
            navArgument("indice") { type = NavType.IntType },
            navArgument("tarea") { type = NavType.BoolType; defaultValue = false },
        ),
    ) { entrada ->
        HabitacionFormScreen(
            indice = entrada.arguments?.getInt("indice"),
            agregarTarea = entrada.arguments?.getBoolean("tarea") ?: false,
            onAtras = { nav.popBackStack() },
        )
    }
    composable(Rutas.AGREGAR_HABITACION) {
        HabitacionFormScreen(indice = null, agregarTarea = false, onAtras = { nav.popBackStack() })
    }
}