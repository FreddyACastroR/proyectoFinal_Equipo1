package mx.itson.hogarea.ui.tareas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.Tarea
import mx.itson.hogarea.datos.nombresDia
import mx.itson.hogarea.datos.textoDias
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

private val formatoFecha = DateTimeFormatter.ofPattern("EEE d MMM", Locale("es", "MX"))

@OptIn(ExperimentalMaterial3Api::class) // SegmentedButton es experimental en Material 3 1.2
@Composable
fun AsignarTareaScreen(id: Int?, onAtras: () -> Unit, onGuardar: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val original = remember(id) { DatosMuestra.tareas.firstOrNull { it.id == id } }
    val editar = original != null
    val hogar = original?.let { DatosMuestra.hogar(it.hogar) } ?: Sesion.hogarActivo
    val borrador = BorradorTarea
    val inicial = original?.asignados ?: listOf(Sesion.usuario)
    val elegidos = remember { mutableStateListOf<Persona>().apply { addAll(inicial) } }
    var porTurnos by rememberSaveable { mutableStateOf(original?.reparto?.startsWith("En equipo") != true) }
    var alAzar by rememberSaveable { mutableStateOf(true) }
    var revuelto by remember { mutableStateOf<List<Persona>?>(null) }
    val base = revuelto ?: inicial
    val orden = base.filter { it in elegidos } + elegidos.filter { it !in base }

    val diasOrdenados = borrador.dias.sorted()
    val turnos = if (diasOrdenados.isNotEmpty()) {
        List(3) { nombresDia[diasOrdenados[it % diasOrdenados.size]] }
    } else {
        listOfNotNull(borrador.fecha?.format(formatoFecha))
    }
    val cuando = if (diasOrdenados.isNotEmpty()) textoDias(borrador.dias) else turnos.firstOrNull().orEmpty()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Column {
                BarraSuperior(if (editar) "Editar tarea" else "Nueva tarea", onAtras = onAtras)
                Text(
                    "Paso 2 de 2 · ¿Quién la hace?",
                    style = MaterialTheme.typography.labelLarge,
                    color = cs.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                LinearProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        },
        bottomBar = {
            BotonesPaso(
                textoIzq = "Atrás",
                onIzq = onAtras,
                textoDer = if (editar) "Guardar cambios" else "Guardar tarea",
                onDer = {
                    val reparto = when {
                        orden.size == 1 -> "Solo una persona"
                        porTurnos -> "Dividir días · ${if (alAzar) "al azar" else "manual"}"
                        else -> "En equipo"
                    }
                    DatosMuestra.guardarTarea(
                        Tarea(
                            id = original?.id ?: DatosMuestra.nuevoIdTarea(),
                            nombre = borrador.nombre,
                            subtitulo = "${borrador.habitacion} · ${hogar.nombre}",
                            dia = original?.dia ?: cuando,
                            hogar = hogar.nombre,
                            habitacion = borrador.habitacion,
                            asignados = orden,
                            estado = original?.estado ?: mx.itson.hogarea.datos.Estado.PENDIENTE,
                            descripcion = borrador.descripcion,
                            recurrencia = borrador.recurrencia,
                            reparto = reparto,
                            tipo = if (borrador.predeterminada) "Predeterminada de ${borrador.habitacion}" else "Manual",
                            dias = borrador.dias,
                            fecha = borrador.fecha,
                            pausadaHasta = original?.pausadaHasta,
                        ),
                    )
                    onGuardar()
                },
                derHabilitado = orden.isNotEmpty(),
            )
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Miembros asignados (uno o varios)", style = MaterialTheme.typography.titleSmall)
            hogar.miembros.chunked(2).forEach { fila ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    fila.forEach { persona ->
                        val marcado = persona in elegidos
                        Card(
                            onClick = { if (marcado) elegidos.remove(persona) else elegidos.add(persona) },
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = if (marcado) cs.secondaryContainer else cs.surfaceContainerLow,
                            ),
                        ) {
                            Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Avatar(persona)
                                Spacer(Modifier.width(8.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(persona.nombre.substringBefore(" "), style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        if (persona == hogar.creador) "Administrador" else DatosMuestra.rolDe(hogar, persona),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = cs.onSurfaceVariant,
                                    )
                                }
                                Checkbox(
                                    checked = marcado,
                                    onCheckedChange = { if (it) elegidos.add(persona) else elegidos.remove(persona) },
                                )
                            }
                        }
                    }
                }
            }
            Text("¿Cómo se reparten?", style = MaterialTheme.typography.titleSmall)
            OpcionReparto("En equipo", "Todos la hacen juntos el mismo día.", !porTurnos) { porTurnos = false }
            OpcionReparto("Dividir días (turnos)", "Se turnan: cada vez le toca a uno.", porTurnos) { porTurnos = true }
            if (porTurnos && orden.size > 1 && turnos.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Orden de los turnos", style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    TextButton(onClick = { revuelto = elegidos.shuffled() }) {
                        Icon(Icons.Filled.Shuffle, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Sortear de nuevo")
                    }
                }
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                    listOf("Manual", "Al azar").forEachIndexed { i, texto ->
                        SegmentedButton(
                            selected = alAzar == (i == 1),
                            onClick = { alAzar = i == 1 },
                            shape = SegmentedButtonDefaults.itemShape(index = i, count = 2),
                        ) { Text(texto) }
                    }
                }
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                    turnos.forEachIndexed { i, turno ->
                        val persona = orden[i % orden.size]
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(turno, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(104.dp))
                            Avatar(persona)
                            Spacer(Modifier.width(12.dp))
                            Text(persona.nombre.substringBefore(" "), style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OpcionReparto(titulo: String, texto: String, seleccionada: Boolean, onClick: () -> Unit) {
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
            Column {
                Text(titulo, style = MaterialTheme.typography.bodyLarge)
                Text(texto, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AsignarTareaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { AsignarTareaScreen(id = null, onAtras = {}, onGuardar = {}) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun AsignarTareaOscuroPreview() {
    HogareaTheme(oscuro = true, onCambiarTema = {}) {
        FondoHogarea { AsignarTareaScreen(id = 1, onAtras = {}, onGuardar = {}) }
    }
}