package mx.itson.hogarea.ui.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 07 y 07d · Detalle de una tarea. Con puedeEditar = false es 07d (solo lectura).
// La pantalla solo dibuja: lo que pasa al tocar cada botón lo decide quien la llama.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetalleTareaScreen(
    id: Int,
    puedeEditar: Boolean,
    onAtras: () -> Unit,
    onEditar: () -> Unit,
    onPausar: () -> Unit,
    onEliminar: () -> Unit,
    onCompletar: () -> Unit,
) {
    val tarea = DatosMuestra.tareas.firstOrNull { it.id == id } ?: return
    val cs = MaterialTheme.colorScheme
    val completada = tarea.estado == Estado.COMPLETADA
    // "Hoy · jueves 15" → "Jueves"; "Viernes 16" → "Viernes"
    val nombreDia = tarea.dia.substringAfter("· ").substringBefore(" ").replaceFirstChar { it.uppercase() }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            BarraSuperior("Detalle de la tarea", onAtras = onAtras) {
                if (puedeEditar) {
                    IconButton(onClick = onEditar) { Icon(Icons.Filled.Edit, contentDescription = "Editar tarea") }
                    IconButton(onClick = { }) { Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones") }
                }
            }
        },
        bottomBar = {
            Button(
                onClick = onCompletar,
                enabled = !completada,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(48.dp),
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (completada) "Completada" else "Marcar como completada")
            }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                when (tarea.estado) {
                    Estado.PENDIENTE -> Etiqueta(Icons.Filled.Schedule, "Pendiente", cs.tertiaryContainer, cs.onTertiaryContainer)
                    Estado.PAUSADA -> Etiqueta(Icons.Filled.Pause, "Pausada", cs.surfaceContainerHighest, cs.onSurfaceVariant)
                    Estado.COMPLETADA -> Etiqueta(Icons.Filled.Check, "Completada", cs.primaryContainer, cs.onPrimaryContainer)
                }
                Etiqueta(Icons.Filled.Weekend, tarea.habitacion, cs.surfaceContainerHigh, cs.onSurface)
                Etiqueta(Icons.Filled.Home, tarea.hogar, cs.surfaceContainerHigh, cs.onSurface)
            }
            Text(tarea.nombre, style = MaterialTheme.typography.headlineMedium)
            if (tarea.descripcion.isNotEmpty()) {
                Text(tarea.descripcion, style = MaterialTheme.typography.bodyLarge, color = cs.onSurfaceVariant)
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                Dato(Icons.Filled.CalendarToday, "Día", nombreDia)
                Dato(Icons.Filled.Repeat, "Recurrencia", tarea.recurrencia)
                Dato(Icons.Filled.Shuffle, "Reparto", tarea.reparto)
                Dato(Icons.Filled.Checklist, "Tipo", tarea.tipo)
            }
            Text("Asignados", style = MaterialTheme.typography.titleSmall)
            tarea.asignados.forEachIndexed { i, persona ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Avatar(persona, tamano = 40.dp)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(persona.nombre.substringBefore(" "), style = MaterialTheme.typography.bodyLarge)
                        Text(
                            when {
                                tarea.asignados.size == 1 -> "Asignada"
                                i == 0 -> "Turno de esta semana"
                                else -> "Siguiente turno"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = if (i == 0) cs.primary else cs.onSurfaceVariant,
                        )
                    }
                }
            }
            if (puedeEditar) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    FilledTonalButton(onClick = onPausar, modifier = Modifier.weight(1f).height(48.dp)) {
                        Icon(Icons.Filled.Pause, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Pausar")
                    }
                    OutlinedButton(onClick = onEliminar, modifier = Modifier.weight(1f).height(48.dp)) {
                        Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Eliminar")
                    }
                }
                Text(
                    "Solo el creador del hogar o las personas asignadas pueden completar esta tarea. Eliminar pide tu huella.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            } else {
                Card(
                    colors = CardDefaults.cardColors(containerColor = cs.tertiaryContainer, contentColor = cs.onTertiaryContainer),
                ) {
                    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Filled.Visibility, contentDescription = null)
                        Text(
                            "En este hogar solo puedes ver tus tareas y completarlas. Editar, pausar y eliminar le toca a Ángel, el creador.",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

// Etiqueta de color: Pendiente, la habitación y el hogar
@Composable
private fun Etiqueta(icono: ImageVector, texto: String, fondo: Color, color: Color) {
    Row(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(fondo)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Text(texto, style = MaterialTheme.typography.labelLarge, color = color)
    }
}

@Composable
private fun Dato(icono: ImageVector, etiqueta: String, valor: String) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(16.dp))
        Text(etiqueta, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(valor, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun DetalleTareaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea {
            DetalleTareaScreen(
                id = 1,
                puedeEditar = true,
                onAtras = {},
                onEditar = {},
                onPausar = {},
                onEliminar = {},
                onCompletar = {},
            )
        }
    }
}