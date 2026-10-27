package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Habitacion
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun HabitacionesScreen(onAtras: () -> Unit, onEditar: () -> Unit, onAgregar: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Habitaciones", onAtras = onAtras) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAgregar,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Habitación") },
                containerColor = cs.primary,
                contentColor = cs.onPrimary,
            )
        },
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    "Define las habitaciones y sus tareas predeterminadas. Al crear una tarea podrás elegirlas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = cs.onSurfaceVariant,
                )
            }
            items(DatosMuestra.habitaciones) { habitacion ->
                TarjetaHabitacion(habitacion, onEditar = onEditar, onEliminar = { })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TarjetaHabitacion(habitacion: Habitacion, onEditar: () -> Unit, onEliminar: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(40.dp).clip(CircleShape).background(cs.secondaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(iconoDe(habitacion.nombre), contentDescription = null, tint = cs.onSecondaryContainer)
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(habitacion.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${habitacion.predeterminadas.size} tareas",
                        style = MaterialTheme.typography.bodySmall,
                        color = cs.onSurfaceVariant,
                    )
                }
                IconButton(onClick = onEditar) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar ${habitacion.nombre}")
                }
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar ${habitacion.nombre}", tint = cs.error)
                }
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                habitacion.predeterminadas.forEach { tarea ->
                    SuggestionChip(onClick = { }, label = { Text(tarea) })
                }
                TextButton(onClick = { }) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text("Tarea")
                }
            }
        }
    }
}

internal fun iconoDe(habitacion: String): ImageVector = when (habitacion) {
    "Cocina" -> Icons.Filled.Kitchen
    "Sala" -> Icons.Filled.Weekend
    "Baño" -> Icons.Filled.Bathtub
    "Recámara principal" -> Icons.Filled.Bed
    "Patio" -> Icons.Filled.Grass
    else -> Icons.Filled.MeetingRoom
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HabitacionesPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { HabitacionesScreen(onAtras = {}, onEditar = {}, onAgregar = {}) }
    }
}