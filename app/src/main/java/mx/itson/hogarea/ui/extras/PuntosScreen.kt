package mx.itson.hogarea.ui.extras

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 12 · Puntos y recompensas (se abre desde el chip de puntos del Perfil)
@Composable
fun PuntosScreen(onAtras: () -> Unit, onHistorial: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val yo = Persona.ANDRE
    val misPuntos = DatosMuestra.puntos.first { it.first == yo }.second
    val (siguiente, meta) = DatosMuestra.recompensas.first() // "Elegir la película" to 150

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Puntos y recompensas", onAtras = onAtras) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Tarjeta grande con mis puntos y el avance hacia la siguiente recompensa
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cs.tertiaryContainer, contentColor = cs.onTertiaryContainer),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("$misPuntos puntos", style = MaterialTheme.typography.headlineMedium)
                    }
                    Text("Este mes en Casa Nainari", style = MaterialTheme.typography.bodyMedium)
                    LinearProgressIndicator(
                        progress = { misPuntos / meta.toFloat() },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = cs.tertiary,
                        trackColor = cs.surfaceContainerLowest,
                    )
                    Text(
                        "Te faltan ${meta - misPuntos} puntos para «$siguiente».",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = cs.onSurfaceVariant)
                Text(
                    "Cada tarea completada a tiempo vale 10 puntos; si la completas tarde, 5.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }

            Titulo("Tabla del hogar · octubre")
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                DatosMuestra.puntos.forEachIndexed { i, (persona, puntos) ->
                    val soyYo = persona == yo
                    ListItem(
                        headlineContent = {
                            Text(
                                if (soyYo) "${persona.nombre} (tú)" else persona.nombre,
                                fontWeight = if (soyYo) FontWeight.Bold else null,
                            )
                        },
                        leadingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("${i + 1}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.width(24.dp))
                                Avatar(persona)
                            }
                        },
                        trailingContent = { Text("$puntos pts", style = MaterialTheme.typography.labelLarge) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            Titulo("Recompensas del hogar")
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                DatosMuestra.recompensas.forEach { (nombre, costo) ->
                    ListItem(
                        headlineContent = { Text(nombre) },
                        supportingContent = { Text("$costo puntos") },
                        leadingContent = { Icon(Icons.Filled.CardGiftcard, contentDescription = null) },
                        // Se puede canjear solo con puntos suficientes (con 120 todavía no)
                        trailingContent = {
                            FilledTonalButton(onClick = { }, enabled = misPuntos >= costo) { Text("Canjear") }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            OutlinedButton(onClick = onHistorial, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(Icons.Filled.History, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Ver historial de tareas")
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Titulo(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PuntosPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { PuntosScreen(onAtras = {}, onHistorial = {}) }
    }
}