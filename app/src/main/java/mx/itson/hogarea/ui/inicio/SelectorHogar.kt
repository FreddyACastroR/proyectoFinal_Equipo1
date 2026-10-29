package mx.itson.hogarea.ui.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.theme.HogareaTheme


@Composable
fun SelectorHogar(valor: String, opciones: List<String>, onElegir: (String) -> Unit, modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    var abierto by remember { mutableStateOf(false) }
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Mostrar:", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
        Box {
            AssistChip(
                onClick = { abierto = true },
                label = { Text(valor) },
                leadingIcon = {
                    Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize))
                },
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                colors = AssistChipDefaults.assistChipColors(containerColor = cs.secondaryContainer),
                border = null,
            )
            DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onElegir(opcion)
                            abierto = false
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectorHogarPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        SelectorHogar("Todos mis hogares", listOf("Todos mis hogares"), onElegir = {}, modifier = Modifier.padding(16.dp))
    }
}