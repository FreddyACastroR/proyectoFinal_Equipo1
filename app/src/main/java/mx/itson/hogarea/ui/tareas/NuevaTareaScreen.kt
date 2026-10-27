package mx.itson.hogarea.ui.tareas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 06a, 06e y 06c · Paso 1 del asistente. editar = true es 06c (Barrer la sala ya llenada)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTareaScreen(editar: Boolean, onCancelar: () -> Unit, onSiguiente: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    var predeterminada by rememberSaveable { mutableStateOf(editar) }
    var nombre by rememberSaveable { mutableStateOf("Trapear la sala") }
    var descripcion by rememberSaveable {
        mutableStateOf(if (editar) "Debajo de sillones y mesa de centro" else "Mover sillones y usar limpiador")
    }
    var habitacion by rememberSaveable { mutableStateOf("Sala") }
    var elegida by rememberSaveable { mutableStateOf(if (editar) "Barrer la sala" else "") }
    var dia by rememberSaveable { mutableIntStateOf(if (editar) 3 else 5) }
    var recurrencia by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Column {
                BarraSuperior(if (editar) "Editar tarea" else "Nueva tarea", onAtras = onCancelar)
                Text(
                    "Paso 1 de 2 · Datos de la tarea",
                    style = MaterialTheme.typography.labelLarge,
                    color = cs.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                LinearProgressIndicator(
                    progress = { 0.5f },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        },
        bottomBar = { BotonesPaso("Cancelar", onCancelar, "Siguiente", onSiguiente) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Etiqueta("Tipo de tarea")
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                listOf("Manual", "Predeterminada").forEachIndexed { i, texto ->
                    SegmentedButton(
                        selected = predeterminada == (i == 1),
                        onClick = { predeterminada = i == 1 },
                        shape = SegmentedButtonDefaults.itemShape(index = i, count = 2),
                    ) { Text(texto) }
                }
            }
            if (!predeterminada) {
                Text(
                    "Las predeterminadas las define el creador del hogar para cada habitación.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la tarea") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción breve") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            CampoMenu(
                etiqueta = "Habitación",
                valor = habitacion,
                opciones = DatosMuestra.habitaciones.map { it.nombre } + listOf("Toda la casa", "No aplica"),
                onElegir = {
                    habitacion = it
                    elegida = ""
                },
                icono = Icons.Filled.Weekend,
            )
            if (!predeterminada) {
                Text(
                    "También puedes elegir “Toda la casa” o “No aplica”.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }
            if (predeterminada) {
                val lista = DatosMuestra.habitaciones.firstOrNull { it.nombre == habitacion }?.predeterminadas.orEmpty()
                Etiqueta("Elige una tarea predeterminada de $habitacion")
                if (lista.isEmpty()) {
                    Text(
                        "Esta habitación no tiene tareas predeterminadas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = cs.onSurfaceVariant,
                    )
                }
                lista.forEach { tarea ->
                    OpcionRadio(tarea, seleccionada = elegida == tarea) { elegida = tarea }
                }
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción breve") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Etiqueta("Día de la semana")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                dias.forEachIndexed { i, letra ->
                    val marcado = dia == i
                    Box(
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (marcado) cs.primary else Color.Transparent)
                            .border(1.dp, if (marcado) cs.primary else cs.outline, CircleShape)
                            .clickable { dia = i },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(letra, color = if (marcado) cs.onPrimary else cs.onSurface)
                    }
                }
            }
            Etiqueta("Recurrencia")
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                recurrencias.forEachIndexed { i, texto ->
                    SegmentedButton(
                        selected = recurrencia == i,
                        onClick = { recurrencia = i },
                        shape = SegmentedButtonDefaults.itemShape(index = i, count = recurrencias.size),
                    ) { Text(texto) }
                }
            }
        }
    }
}

private val dias = listOf("L", "M", "M", "J", "V", "S", "D")
private val recurrencias = listOf("Semanal", "Cada 15 días", "Mensual")

@Composable
private fun Etiqueta(texto: String) {
    Text(texto, style = MaterialTheme.typography.titleSmall)
}

// Campo de solo lectura que abre un menú con la flecha
@Composable
private fun CampoMenu(
    etiqueta: String,
    valor: String,
    opciones: List<String>,
    onElegir: (String) -> Unit,
    icono: ImageVector? = null,
) {
    var abierto by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            leadingIcon = if (icono != null) {
                { Icon(icono, contentDescription = null) }
            } else {
                null
            },
            trailingIcon = {
                IconButton(onClick = { abierto = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Elegir $etiqueta")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
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

@Composable
private fun OpcionRadio(texto: String, seleccionada: Boolean, onClick: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada) cs.secondaryContainer else cs.surfaceContainerLow,
        ),
        border = if (seleccionada) BorderStroke(1.dp, cs.primary) else null,
    ) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = seleccionada, onClick = onClick)
            Text(texto, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// Los dos botones de abajo del asistente (también los usa AsignarTareaScreen)
@Composable
internal fun BotonesPaso(textoIzq: String, onIzq: () -> Unit, textoDer: String, onDer: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OutlinedButton(onClick = onIzq, modifier = Modifier.weight(1f).height(48.dp)) { Text(textoIzq) }
        Button(onClick = onDer, modifier = Modifier.weight(1f).height(48.dp)) { Text(textoDer) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NuevaTareaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { NuevaTareaScreen(editar = false, onCancelar = {}, onSiguiente = {}) }
    }
}