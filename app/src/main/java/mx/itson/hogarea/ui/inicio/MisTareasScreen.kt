package mx.itson.hogarea.ui.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.BarraInferior
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

private const val TODOS = "Todos mis hogares"

@Composable
fun MisTareasScreen(
    soloLectura: Boolean,
    onIrPestana: (String) -> Unit,
    onNotificaciones: () -> Unit,
    onNuevaTarea: () -> Unit,
    onAbrirTarea: (Int) -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    var hogar by rememberSaveable { mutableStateOf(if (soloLectura) DatosMuestra.depaObregon.nombre else TODOS) }
    val opciones = listOf(TODOS) + DatosMuestra.hogares.map { it.nombre }

    // 05: las tareas de Rubén (el usuario de la muestra). 05b: todas las del hogar.
    val tareas = DatosMuestra.tareas.filter {
        if (soloLectura) it.hogar == hogar
        else Persona.RUBEN in it.asignados && (hogar == TODOS || it.hogar == hogar)
    }
    val deHoy = tareas.filter { it.dia.startsWith("Hoy") }
    val hechasHoy = deHoy.count { it.estado == Estado.COMPLETADA }
    val totalHoy = deHoy.count { it.estado != Estado.PAUSADA }
    val puedeCrear = !soloLectura &&
            (hogar == TODOS || DatosMuestra.hogares.first { it.nombre == hogar }.miembrosPuedenEditar)

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            BarraSuperior("Mis tareas") {
                IconButton(onClick = { }) { Icon(Icons.Filled.CalendarMonth, contentDescription = "Calendario") }
                IconButton(onClick = onNotificaciones) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
                }
            }
        },
        bottomBar = { BarraInferior(Rutas.MIS_TAREAS, onIrPestana) },
        floatingActionButton = {
            if (puedeCrear) {
                ExtendedFloatingActionButton(
                    onClick = onNuevaTarea,
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    text = { Text("Nueva tarea") },
                    containerColor = cs.primary,
                    contentColor = cs.onPrimary,
                )
            }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SelectorHogar(hogar, if (soloLectura) listOf(hogar) else opciones, onElegir = { hogar = it })
            if (soloLectura) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = cs.tertiaryContainer, contentColor = cs.onTertiaryContainer),
                ) {
                    Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Filled.Visibility, contentDescription = null)
                        Text(
                            "Solo puedes ver las tareas de este hogar. Puedes completar únicamente las que te asignaron.",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Semana()
            ProgresoDelDia(if (soloLectura) "$hogar · hoy" else "Hoy, jueves 15", hechasHoy, totalHoy)
            ListaTareas(tareas = tareas, onAbrir = onAbrirTarea, modifier = Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MisTareasPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea {
            MisTareasScreen(
                soloLectura = false,
                onIrPestana = {},
                onNotificaciones = {},
                onNuevaTarea = {},
                onAbrirTarea = {},
            )
        }
    }
}