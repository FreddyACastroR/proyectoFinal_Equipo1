package mx.itson.hogarea.ui.hogar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

private val todas = listOf("Cocina", "Sala", "Baño", "Recámara principal", "Patio", "Lavandería", "Cochera")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CrearHogarScreen(onAtras: () -> Unit, onCrear: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    var nombre by rememberSaveable { mutableStateOf("Casa Nainari") }
    val elegidas = remember { mutableStateListOf("Cocina", "Sala", "Baño", "Recámara principal", "Patio") }
    var puedenEditar by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Crear hogar", onAtras = onAtras) },
        bottomBar = {
            Button(
                onClick = onCrear,
                enabled = nombre.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(48.dp),
            ) { Text("Crear hogar") }
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
                label = { Text("Nombre del hogar") },
                leadingIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Habitaciones", style = MaterialTheme.typography.titleSmall, color = cs.primary)
                Text(
                    "Elige las que tiene tu casa. Podrás agregar más después.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                todas.forEach { habitacion ->
                    val marcada = habitacion in elegidas
                    FilterChip(
                        selected = marcada,
                        onClick = { if (marcada) elegidas.remove(habitacion) else elegidas.add(habitacion) },
                        label = { Text(habitacion) },
                        leadingIcon = if (marcada) {
                            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                        } else {
                            null
                        },
                    )
                }
                TextButton(onClick = { }) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text("Otra")
                }
            }
            Text("Permisos de los miembros", style = MaterialTheme.typography.titleSmall, color = cs.primary)
            OpcionPermiso("Solo pueden ver las tareas", "Únicamente tú creas, editas y eliminas tareas.", !puedenEditar) {
                puedenEditar = false
            }
            OpcionPermiso("Pueden ver y editar", "Los miembros también pueden crear y editar tareas.", puedenEditar) {
                puedenEditar = true
            }
            Text(
                "Puedes cambiar esto cuando quieras en Configuración del hogar.",
                style = MaterialTheme.typography.bodySmall,
                color = cs.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun OpcionPermiso(titulo: String, texto: String, seleccionada: Boolean, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada) cs.secondaryContainer else cs.surfaceContainerLowest,
        ),
        border = BorderStroke(1.dp, if (seleccionada) cs.primary else cs.outlineVariant),
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = seleccionada, onClick = onClick)
            Column {
                Text(titulo, style = MaterialTheme.typography.bodyLarge)
                Text(texto, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun CrearHogarPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { CrearHogarScreen(onAtras = {}, onCrear = {}) }
    }
}