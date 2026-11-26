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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.esAdmin
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 12 · Puntos y recompensas (se abre desde el chip de puntos del Perfil)
@Composable
fun PuntosScreen(onAtras: () -> Unit, onHistorial: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val yo = Sesion.usuario
    val hogar = Sesion.hogarActivo
    // Ganados: lo que sumaste en el mes y ordena el podio. Disponibles: lo que puedes gastar.
    val ganados = DatosMuestra.puntos.firstOrNull { it.first == yo }?.second ?: 0
    val disponibles = DatosMuestra.disponibles[yo] ?: 0
    // La recompensa más barata que todavía no alcanzas
    val siguiente = DatosMuestra.recompensas.filter { it.second > disponibles }.minByOrNull { it.second }
    val misCanjes = DatosMuestra.canjes.filter { it.first == yo }
    var porCanjear by remember { mutableStateOf<Pair<String, Int>?>(null) } // 12b
    var nuevaAbierta by remember { mutableStateOf(false) }                  // 12d

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Puntos y recompensas", onAtras = onAtras) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Mis puntos y el avance hacia la siguiente recompensa
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cs.tertiaryContainer, contentColor = cs.onTertiaryContainer),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("$disponibles puntos", style = MaterialTheme.typography.headlineMedium)
                    }
                    Text(
                        "Disponibles para canjear · ganaste $ganados este mes en ${hogar.nombre}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    if (siguiente != null) {
                        LinearProgressIndicator(
                            progress = { (disponibles / siguiente.second.toFloat()).coerceIn(0f, 1f) },
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = cs.tertiary,
                            trackColor = cs.surfaceContainerLowest,
                        )
                        Text(
                            "Te faltan ${siguiente.second - disponibles} puntos para «${siguiente.first}».",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    } else {
                        Text("Ya te alcanza para cualquier recompensa.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(18.dp), tint = cs.onSurfaceVariant)
                Text(
                    "Cada tarea completada te da 10 puntos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = cs.onSurfaceVariant,
                )
            }

            // 6.2 · Trofeos para los tres primeros
            Titulo("Podio de octubre")
            Podio(DatosMuestra.puntos.take(3))
            if (DatosMuestra.puntos.size > 3) {
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                    DatosMuestra.puntos.drop(3).forEachIndexed { i, (persona, puntos) ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    if (persona == yo) "${persona.nombre} (tú)" else persona.nombre,
                                    fontWeight = if (persona == yo) FontWeight.Bold else null,
                                )
                            },
                            leadingContent = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("${i + 4}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.width(24.dp))
                                    Avatar(persona)
                                }
                            },
                            trailingContent = { Text("$puntos pts", style = MaterialTheme.typography.labelLarge) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        )
                    }
                }
            }

            // 6.2 · Reconocimientos del mes
            Titulo("Reconocimientos del mes")
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                val (masActiva, hechas) = DatosMuestra.completadasDelMes.maxBy { it.second }
                val todos = listOf(Triple("Más tareas completadas", masActiva, "$hechas tareas")) + DatosMuestra.reconocimientos
                todos.forEach { (titulo, persona, motivo) ->
                    ListItem(
                        headlineContent = { Text(titulo) },
                        supportingContent = { Text("${persona.nombre.substringBefore(" ")} · $motivo") },
                        leadingContent = { Icon(Icons.Filled.MilitaryTech, contentDescription = null, tint = cs.tertiary) },
                        trailingContent = { Avatar(persona) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Titulo("Recompensas del hogar", Modifier.weight(1f))
                // Solo quien administra el hogar crea recompensas
                if (esAdmin(hogar)) {
                    TextButton(onClick = { nuevaAbierta = true }) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Nueva")
                    }
                }
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                DatosMuestra.recompensas.forEach { recompensa ->
                    val (nombre, costo) = recompensa
                    ListItem(
                        headlineContent = { Text(nombre) },
                        supportingContent = {
                            Text(if (disponibles >= costo) "$costo puntos" else "$costo puntos · te faltan ${costo - disponibles}")
                        },
                        leadingContent = { Icon(Icons.Filled.CardGiftcard, contentDescription = null) },
                        trailingContent = {
                            FilledTonalButton(onClick = { porCanjear = recompensa }, enabled = disponibles >= costo) { Text("Canjear") }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            // 12c · Lo que ya canjeaste
            if (misCanjes.isNotEmpty()) {
                Titulo("Mis canjes")
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                    misCanjes.forEach { (_, nombre, costo) ->
                        ListItem(
                            headlineContent = { Text(nombre) },
                            supportingContent = { Text("Canjeada por $costo puntos") },
                            leadingContent = { Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = cs.primary) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        )
                    }
                }
            }

            OutlinedButton(onClick = onHistorial, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(Icons.Filled.History, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Ver historial de tareas")
            }
        }
    }

    // 12b · Confirmar el canje
    porCanjear?.let { recompensa ->
        AlertDialog(
            onDismissRequest = { porCanjear = null },
            title = { Text("¿Canjear «${recompensa.first}»?") },
            text = { Text("Se descuentan ${recompensa.second} puntos. Te quedan ${disponibles - recompensa.second}.") },
            confirmButton = {
                TextButton(onClick = {
                    DatosMuestra.canjear(yo, recompensa)
                    porCanjear = null
                }) { Text("Canjear") }
            },
            dismissButton = { TextButton(onClick = { porCanjear = null }) { Text("Cancelar") } },
        )
    }

    // 12d · Nueva recompensa (solo administrador)
    if (nuevaAbierta) {
        var nombre by remember { mutableStateOf("") }
        var costo by remember { mutableStateOf("") }
        val puntos = costo.toIntOrNull()
        AlertDialog(
            onDismissRequest = { nuevaAbierta = false },
            title = { Text("Nueva recompensa") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Recompensa") },
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = costo,
                        onValueChange = { costo = it.filter { c -> c.isDigit() }.take(4) },
                        label = { Text("Costo en puntos") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        DatosMuestra.recompensas.add(nombre.trim() to (puntos ?: 0))
                        nuevaAbierta = false
                    },
                    enabled = nombre.isNotBlank() && puntos != null && puntos > 0,
                ) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { nuevaAbierta = false }) { Text("Cancelar") } },
        )
    }
}

@Composable
private fun Titulo(texto: String, modifier: Modifier = Modifier) {
    Text(
        texto,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(top = 8.dp),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PuntosPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { PuntosScreen(onAtras = {}, onHistorial = {}) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PuntosOscuroPreview() {
    HogareaTheme(oscuro = true, onCambiarTema = {}) {
        FondoHogarea { PuntosScreen(onAtras = {}, onHistorial = {}) }
    }
}