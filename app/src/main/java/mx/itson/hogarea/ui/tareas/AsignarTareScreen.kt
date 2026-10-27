package mx.itson.hogarea.ui.tareas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 06b y 06d · Paso 2 del asistente: quién la hace y cómo se reparten
@OptIn(ExperimentalMaterial3Api::class) // SegmentedButton es experimental en Material 3 1.2
@Composable
fun AsignarTareaScreen(editar: Boolean, onAtras: () -> Unit, onGuardar: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val hogar = DatosMuestra.casaNainari
    val inicial = if (editar) listOf(Persona.RUBEN, Persona.FREDDY) else listOf(Persona.ANDRE, Persona.ANGEL, Persona.RUBEN)
    val elegidos = remember { mutableStateListOf<Persona>().apply { addAll(inicial) } }
    var porTurnos by rememberSaveable { mutableStateOf(true) }

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
        bottomBar = { BotonesPaso("Atrás", onAtras, if (editar) "Guardar cambios" else "Guardar tarea", onGuardar) },
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
                                        if (persona == hogar.creador) "Creador" else "Roomie",
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
            OpcionReparto(
                "Dividir días (turnos)",
                "Se turnan: cada ${if (editar) "jueves" else "sábado"} le toca a uno.",
                porTurnos,
            ) { porTurnos = true }
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
        FondoHogarea { AsignarTareaScreen(editar = false, onAtras = {}, onGuardar = {}) }
    }
}