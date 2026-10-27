package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun HabitacionFormScreen(editar: Boolean, onAtras: () -> Unit) {
    val cocina = DatosMuestra.habitaciones.first()
    val cs = MaterialTheme.colorScheme
    var nombre by rememberSaveable { mutableStateOf(if (editar) cocina.nombre else "Lavandería") }
    var icono by rememberSaveable { mutableIntStateOf(if (editar) 0 else 5) }
    val tareas = remember { mutableStateListOf<String>().apply { if (editar) addAll(cocina.predeterminadas) } }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior(if (editar) "Editar habitación" else "Agregar habitación", onAtras = onAtras) },
        bottomBar = {
            Row(
                Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(onClick = onAtras, modifier = Modifier.weight(1f).height(48.dp)) { Text("Cancelar") }
                Button(onClick = onAtras, modifier = Modifier.weight(1f).height(48.dp)) { Text("Guardar") }
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
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la habitación") },
                leadingIcon = { Icon(iconos[icono], contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Text("Ícono", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                iconos.forEachIndexed { i, imagen ->
                    FilledIconToggleButton(checked = icono == i, onCheckedChange = { icono = i }) {
                        Icon(imagen, contentDescription = null)
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Tareas predeterminadas", style = MaterialTheme.typography.titleSmall)
                Text(
                    "Aparecen al elegir \"Predeterminada\" al crear una tarea.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                if (tareas.isEmpty()) {
                    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Filled.Checklist, contentDescription = null, tint = cs.onSurfaceVariant)
                        Text(
                            "Aún no hay tareas predeterminadas. Agrega las que se repiten en esta habitación.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = cs.onSurfaceVariant,
                        )
                    }
                } else {
                    tareas.forEach { tarea ->
                        ListItem(
                            headlineContent = { Text(tarea) },
                            trailingContent = {
                                IconButton(onClick = { tareas.remove(tarea) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Quitar $tarea", tint = cs.error)
                                }
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        )
                    }
                }
            }
            OutlinedButton(onClick = { tareas.add("Nueva tarea ${tareas.size + 1}") }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Agregar tarea predeterminada")
            }
        }
    }
}

private val iconos = listOf(
    Icons.Filled.Kitchen,
    Icons.Filled.Weekend,
    Icons.Filled.Bathtub,
    Icons.Filled.Bed,
    Icons.Filled.CleaningServices,
    Icons.Filled.MeetingRoom,
)

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HabitacionFormPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { HabitacionFormScreen(editar = true, onAtras = {}) }
    }
}