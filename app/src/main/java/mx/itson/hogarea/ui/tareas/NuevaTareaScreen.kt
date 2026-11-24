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
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.nombresDia
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.componentes.CampoFecha
import mx.itson.hogarea.ui.componentes.CampoSelector
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import java.time.LocalDate

internal object BorradorTarea {
    var predeterminada = false
    var nombre = ""
    var descripcion = ""
    var habitacion = "Sala"
    var dias: Set<Int> = emptySet()
    var fecha: LocalDate? = null
    var recurrencia = "Semanal"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTareaScreen(id: Int?, onCancelar: () -> Unit, onSiguiente: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val original = remember(id) { DatosMuestra.tareas.firstOrNull { it.id == id } }
    val editar = original != null
    var predeterminada by rememberSaveable { mutableStateOf(original?.tipo?.startsWith("Predeterminada") ?: false) }
    var nombre by rememberSaveable { mutableStateOf(original?.nombre ?: "") }
    var descripcion by rememberSaveable { mutableStateOf(original?.descripcion ?: "") }
    var habitacion by rememberSaveable { mutableStateOf(original?.habitacion ?: "Sala") }
    var elegida by rememberSaveable { mutableStateOf(if (predeterminada) original?.nombre ?: "" else "") }
    var dias by rememberSaveable { mutableStateOf(original?.dias ?: emptySet()) }
    var fecha by rememberSaveable { mutableStateOf(original?.fecha) }
    var recurrencia by rememberSaveable { mutableIntStateOf(recurrencias.indexOf(original?.recurrencia).coerceAtLeast(0)) }

    val completo = if (predeterminada) elegida.isNotEmpty() && dias.isNotEmpty() else nombre.isNotBlank() && fecha != null

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
        bottomBar = {
            BotonesPaso(
                textoIzq = "Cancelar",
                onIzq = onCancelar,
                textoDer = "Siguiente",
                onDer = {
                    BorradorTarea.predeterminada = predeterminada
                    BorradorTarea.nombre = if (predeterminada) elegida else nombre.trim()
                    BorradorTarea.descripcion = descripcion.trim()
                    BorradorTarea.habitacion = habitacion
                    BorradorTarea.dias = if (predeterminada) dias else emptySet()
                    BorradorTarea.fecha = if (predeterminada) null else fecha
                    BorradorTarea.recurrencia = if (predeterminada) recurrencias[recurrencia] else "Una sola vez"
                    onSiguiente()
                },
                derHabilitado = completo,
            )
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
                    "Una tarea manual se hace una sola vez, en la fecha que elijas.",
                    style = MaterialTheme.typography.bodyMedium,
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
            CampoSelector(
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

                CampoFecha("Fecha", fecha, { fecha = it }, Modifier.fillMaxWidth())
            } else {
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

                Etiqueta("Días de la semana")
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    letrasDia.forEachIndexed { i, letra ->
                        val marcado = i in dias
                        Box(
                            Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (marcado) cs.primary else Color.Transparent)
                                .border(1.dp, if (marcado) cs.primary else cs.outline, CircleShape)
                                .clickable(onClickLabel = nombresDia[i]) { dias = if (marcado) dias - i else dias + i },
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
}

private val letrasDia = listOf("L", "M", "M", "J", "V", "S", "D")
private val recurrencias = listOf("Semanal", "Cada 15 días", "Mensual")

@Composable
private fun Etiqueta(texto: String) {
    Text(texto, style = MaterialTheme.typography.titleSmall)
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


@Composable
internal fun BotonesPaso(
    textoIzq: String,
    onIzq: () -> Unit,
    textoDer: String,
    onDer: () -> Unit,
    derHabilitado: Boolean = true,
) {
    Row(
        Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OutlinedButton(onClick = onIzq, modifier = Modifier.weight(1f).height(48.dp)) { Text(textoIzq) }
        Button(onClick = onDer, enabled = derHabilitado, modifier = Modifier.weight(1f).height(48.dp)) { Text(textoDer) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NuevaTareaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { NuevaTareaScreen(id = null, onCancelar = {}, onSiguiente = {}) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NuevaTareaOscuroPreview() {
    HogareaTheme(oscuro = true, onCambiarTema = {}) {
        FondoHogarea { NuevaTareaScreen(id = 1, onCancelar = {}, onSiguiente = {}) }
    }
}