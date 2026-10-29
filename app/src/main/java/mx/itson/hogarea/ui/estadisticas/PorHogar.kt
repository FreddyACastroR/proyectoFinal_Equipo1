package mx.itson.hogarea.ui.estadisticas

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.R
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.theme.Exito
import mx.itson.hogarea.ui.theme.ExitoOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

internal val meses = listOf("Octubre 2026", "Septiembre 2026", "Agosto 2026")

// 09 · Pestaña "Por hogar"
@OptIn(ExperimentalMaterial3Api::class) // SegmentedButton es experimental en Material 3 1.2
@Composable
fun PorHogar(modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val verde = if (LocalTema.current.oscuro) ExitoOscuro else Exito
    var hogar by rememberSaveable { mutableStateOf("Casa Nainari") }
    var mes by rememberSaveable { mutableStateOf(meses.first()) }
    var periodo by rememberSaveable { mutableIntStateOf(1) } // 0 Día, 1 Mes, 2 Año

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Filtro(Icons.Filled.Home, hogar, DatosMuestra.hogares.map { it.nombre }) { hogar = it }
            Filtro(Icons.Filled.CalendarMonth, mes, meses) { mes = it }
        }
        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
            listOf("Día", "Mes", "Año").forEachIndexed { i, texto ->
                SegmentedButton(
                    selected = periodo == i,
                    onClick = { periodo = i },
                    shape = SegmentedButtonDefaults.itemShape(index = i, count = 3),
                ) { Text(texto) }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Indicador("42", "Completadas", cs.primary, Modifier.weight(1f))
            Indicador("6", "No realizadas", cs.error, Modifier.weight(1f))
            Indicador("88%", "Cumplimiento", verde, Modifier.weight(1f))
        }
        TarjetaGrafica("Tareas completadas por miembro", "$mes · $hogar", R.drawable.grafica_por_miembro)
        TarjetaGrafica("Reparto de tareas en el hogar", null, R.drawable.grafica_reparto)
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
            Row(Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Tareas no realizadas", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                TextButton(onClick = { }) { Text("Ver las 6") }
            }
            ListItem(
                headlineContent = { Text("Limpiar el baño") },
                supportingContent = { Text("Ángel · vie 3 oct") },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            )
            ListItem(
                headlineContent = { Text("Sacar la basura") },
                supportingContent = { Text("Freddy · sáb 11 oct") },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            )
        }
    }
}

// Botón de filtro con menú: hogar y mes
@Composable
internal fun Filtro(icono: ImageVector, valor: String, opciones: List<String>, onElegir: (String) -> Unit) {
    var abierto by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { abierto = true }, contentPadding = PaddingValues(horizontal = 12.dp)) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text(valor)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
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


@Composable
internal fun Indicador(valor: String, texto: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
        Column(Modifier.padding(12.dp)) {
            Text(valor, style = MaterialTheme.typography.headlineSmall, color = color)
            Text(texto, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}


@Composable
internal fun TarjetaGrafica(titulo: String, subtitulo: String?, @DrawableRes imagen: Int) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleSmall)
            if (subtitulo != null) {
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Image(
                painter = painterResource(imagen),
                contentDescription = titulo,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PorHogarPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { PorHogar(Modifier.fillMaxSize()) }
    }
}