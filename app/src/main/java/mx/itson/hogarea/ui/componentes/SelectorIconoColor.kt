package mx.itson.hogarea.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.theme.LocalTema
import mx.itson.hogarea.ui.theme.coloresEtiqueta

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectorIconoColor(
    iconos: List<ImageVector>,
    icono: Int,
    onIcono: (Int) -> Unit,
    color: Int,
    onColor: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cs = MaterialTheme.colorScheme
    val oscuro = LocalTema.current.oscuro
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Ícono", style = MaterialTheme.typography.titleSmall)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            iconos.forEachIndexed { i, imagen ->
                FilledIconToggleButton(checked = icono == i, onCheckedChange = { onIcono(i) }) {
                    Icon(imagen, contentDescription = null)
                }
            }
        }
        Text("Color", style = MaterialTheme.typography.titleSmall)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            coloresEtiqueta.forEachIndexed { i, c ->
                val elegido = color == i
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (oscuro) c.oscuro else c.claro)
                        .border(if (elegido) 3.dp else 1.dp, if (elegido) cs.primary else cs.outlineVariant, CircleShape)
                        .clickable(onClickLabel = "Elegir ${c.nombre}") { onColor(i) },
                    contentAlignment = Alignment.Center,
                ) {
                    if (elegido) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "${c.nombre}, elegido",
                            tint = if (oscuro) c.enOscuro else c.enClaro,
                        )
                    }
                }
            }
        }
    }
}