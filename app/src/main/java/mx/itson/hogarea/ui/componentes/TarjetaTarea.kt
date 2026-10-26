package mx.itson.hogarea.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.datos.Tarea

// La tarjeta de tarea pendiente, pausada o completada
@Composable
fun TarjetaTarea(tarea: Tarea, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val (icono, color) = when (tarea.estado) {
        Estado.COMPLETADA -> Icons.Filled.CheckCircle to cs.primary
        Estado.PAUSADA -> Icons.Filled.Pause to cs.tertiary
        Estado.PENDIENTE -> Icons.Outlined.RadioButtonUnchecked to cs.onSurfaceVariant
    }
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow),
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icono, contentDescription = null, tint = color)
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    tarea.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (tarea.estado == Estado.COMPLETADA) TextDecoration.LineThrough else null,
                )
                Text(
                    tarea.subtitulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (tarea.estado == Estado.PAUSADA) cs.tertiary else cs.onSurfaceVariant,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                tarea.asignados.forEach { Avatar(it) }
            }
        }
    }
}