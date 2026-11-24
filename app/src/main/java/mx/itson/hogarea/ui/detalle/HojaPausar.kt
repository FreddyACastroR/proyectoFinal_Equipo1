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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Tarea
import mx.itson.hogarea.ui.componentes.CampoFecha
import java.time.LocalDate

private val hoy: LocalDate = LocalDate.of(2026, 11, 24)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HojaPausar(tarea: Tarea, onCancelar: () -> Unit, onPausar: (LocalDate) -> Unit) {
    var desde by remember { mutableStateOf(hoy) }
    var hasta by remember { mutableStateOf(hoy.plusWeeks(1)) }
    val periodos = listOf("1 semana" to desde.plusWeeks(1), "2 semanas" to desde.plusWeeks(2), "1 mes" to desde.plusMonths(1))
    val valido = !hasta.isBefore(desde)
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
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                periodos.forEach { (texto, fin) ->
                    val elegido = hasta == fin
                    FilterChip(
                        selected = elegido,
                        onClick = { hasta = fin },
                        label = { Text(texto, style = MaterialTheme.typography.labelLarge) },
                        leadingIcon = if (elegido) {
                            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                        } else {
                            null
                        },
                    )
                }
            }

            CampoFecha("Desde", desde, { desde = it }, Modifier.fillMaxWidth())
            CampoFecha("Hasta", hasta, { hasta = it }, Modifier.fillMaxWidth())
            if (!valido) {
                Text(
                    "«Hasta» no puede ser anterior a «Desde».",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onCancelar) { Text("Cancelar") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { onPausar(hasta) }, enabled = valido) {
                    Icon(Icons.Filled.Pause, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Pausar")
                }
            }
        }
    }
}