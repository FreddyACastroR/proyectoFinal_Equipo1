package mx.itson.hogarea.ui.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.theme.HogareaTheme
import kotlin.math.roundToInt

@Composable
fun Semana(modifier: Modifier = Modifier, hoy: Int = 15) {
    val cs = MaterialTheme.colorScheme
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("L", "M", "M", "J", "V", "S", "D").forEachIndexed { i, letra ->
            val numero = 12 + i
            val esHoy = numero == hoy
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(letra, style = MaterialTheme.typography.labelMedium, color = cs.onSurfaceVariant)
                Box(
                    Modifier.size(36.dp).clip(CircleShape).background(if (esHoy) cs.primary else Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("$numero", style = MaterialTheme.typography.titleMedium, color = if (esHoy) cs.onPrimary else cs.onSurface)
                }
                // Puntito: días con tareas
                Box(Modifier.size(4.dp).clip(CircleShape).background(if (numero < 18) cs.primary else cs.outlineVariant))
            }
        }
    }
}

@Composable
fun ProgresoDelDia(titulo: String, completadas: Int, total: Int, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val avance = if (total == 0) 0f else completadas.toFloat() / total
    Card(modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(titulo, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "$completadas de $total tareas completadas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = cs.onSurfaceVariant,
                    )
                }
                Text("${(avance * 100).roundToInt()}%", style = MaterialTheme.typography.headlineSmall, color = cs.primary)
            }
            LinearProgressIndicator(progress = { avance }, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SemanaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Semana()
            ProgresoDelDia("Hoy, jueves 15", completadas = 3, total = 5)
        }
    }
}