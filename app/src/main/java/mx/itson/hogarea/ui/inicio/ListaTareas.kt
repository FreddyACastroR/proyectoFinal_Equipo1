package mx.itson.hogarea.ui.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.datos.Tarea
import mx.itson.hogarea.ui.componentes.TarjetaTarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun ListaTareas(tareas: List<Tarea>, onAbrir: (Int) -> Unit, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val porDia = tareas.groupBy { it.dia }
    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 88.dp), // espacio para el botón flotante
    ) {
        porDia.forEach { (dia, delDia) ->
            item(key = dia) {
                val hechas = delDia.count { it.estado == Estado.COMPLETADA }
                val total = delDia.count { it.estado != Estado.PAUSADA }
                Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(dia, style = MaterialTheme.typography.titleSmall, color = cs.primary, modifier = Modifier.weight(1f))
                    Text("$hechas/$total", style = MaterialTheme.typography.labelLarge, color = cs.onSurfaceVariant)
                }
            }
            items(delDia, key = { it.id }) { tarea ->
                TarjetaTarea(tarea, onClick = { onAbrir(tarea.id) })
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ListaTareasPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        ListaTareas(DatosMuestra.tareas.take(8), onAbrir = {}, modifier = Modifier.padding(16.dp))
    }
}