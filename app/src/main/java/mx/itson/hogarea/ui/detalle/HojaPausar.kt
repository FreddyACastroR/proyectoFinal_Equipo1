package mx.itson.hogarea.ui.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Tarea

// 07b · Hoja para pausar una tarea
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HojaPausar(tarea: Tarea, onCancelar: () -> Unit, onPausar: () -> Unit) {
    var periodo by remember { mutableIntStateOf(0) }
    val hasta = listOf("jue 22 oct", "jue 29 oct", "dom 15 nov")[periodo]
    // "Rubén ni para Freddy", como en el Figma
    val nombres = tarea.asignados.map { it.nombre.substringBefore(" ") }
    val quienes = if (nombres.size == 1) nombres.first() else nombres.dropLast(1).joinToString(", ") + " ni para " + nombres.last()

    ModalBottomSheet(onDismissRequest = onCancelar) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Pausar tarea", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Mientras esté pausada, “${tarea.nombre}” no aparecerá como pendiente para $quienes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("1 semana", "2 semanas", "1 mes").forEachIndexed { i, texto ->
                    FilterChip(
                        selected = periodo == i,
                        onClick = { periodo = i },
                        label = { Text(texto) },
                        leadingIcon = if (periodo == i) {
                            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                        } else {
                            null
                        },
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoFecha("Desde", "jue 15 oct", Modifier.weight(1f))
                CampoFecha("Hasta", hasta, Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onCancelar) { Text("Cancelar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = onPausar) {
                    Icon(Icons.Filled.Pause, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Pausar")
                }
            }
        }
    }
}

@Composable
private fun CampoFecha(etiqueta: String, valor: String, modifier: Modifier) {
    OutlinedTextField(
        value = valor,
        onValueChange = {},
        readOnly = true,
        label = { Text(etiqueta) },
        leadingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = null) },
        singleLine = true,
        modifier = modifier,
    )
}