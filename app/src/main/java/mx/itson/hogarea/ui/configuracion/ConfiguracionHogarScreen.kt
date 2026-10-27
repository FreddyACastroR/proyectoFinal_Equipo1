package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.componentes.BarraInferior
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun ConfiguracionHogarScreen(
    onIrPestana: (String) -> Unit,
    onMiembros: () -> Unit,
    onHabitaciones: () -> Unit,
    onVerComoMiembro: () -> Unit,
) {
    val hogar = DatosMuestra.casaNainari
    val cs = MaterialTheme.colorScheme
    var menuAbierto by remember { mutableStateOf(false) }
    var miembrosEditan by remember { mutableStateOf(hogar.miembrosPuedenEditar) }
    var recordatorios by remember { mutableStateOf(true) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var nombreHogar by remember { mutableStateOf(hogar.nombre) }

    val nombres = hogar.miembros.map { it.nombre.substringBefore(" ") }
    val listaMiembros = nombres.dropLast(1).joinToString(", ") + " y " + nombres.last()
    val totalPredeterminadas = DatosMuestra.habitaciones.sumOf { it.predeterminadas.size }

    Scaffold(
        containerColor = Color.Transparent, // el fondo lo pone FondoHogarea
        topBar = { BarraSuperior("Configuración del hogar") }, // sin flecha: es una pestaña
        bottomBar = { BarraInferior(Rutas.CONFIG_HOGAR, onIrPestana) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Hogar:", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
                Box {
                    AssistChip(
                        onClick = { menuAbierto = true },
                        label = { Text(hogar.nombre) },
                        leadingIcon = {
                            Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize))
                        },
                        trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                        colors = AssistChipDefaults.assistChipColors(containerColor = cs.secondaryContainer),
                        border = null,
                    )
                    DropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
                        DatosMuestra.hogares.forEach { h ->
                            DropdownMenuItem(
                                text = { Text(h.nombre) },
                                onClick = {
                                    menuAbierto = false
                                    if (h != hogar) onVerComoMiembro()
                                },
                            )
                        }
                    }
                }
            }

            Seccion("General")
            Fila(Icons.Filled.Edit, "Nombre del hogar", nombreHogar) { mostrarDialogo = true }
            Fila(Icons.Filled.Key, "Código de invitación", "${hogar.codigo} · vence en 7 días", onMiembros)
            Fila(Icons.Filled.Group, "Miembros (${hogar.miembros.size})", listaMiembros, onMiembros)

            Seccion("Tareas")
            Fila(
                Icons.Filled.MeetingRoom,
                "Habitaciones y tareas",
                "${DatosMuestra.habitaciones.size} habitaciones · $totalPredeterminadas predeterminadas",
                onHabitaciones,
            )

            Seccion("Permisos y notificaciones")
            FilaInterruptor(
                Icons.Filled.Edit,
                "Los miembros pueden editar",
                if (miembrosEditan) "Ahora: pueden ver y editar" else "Ahora: solo pueden ver",
                miembrosEditan,
            ) { miembrosEditan = it }
            FilaInterruptor(Icons.Filled.Notifications, "Recordatorios para todos", "Avisar a cada miembro", recordatorios) {
                recordatorios = it
            }

            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = cs.onSurfaceVariant)
                Text(
                    "Solo tú, como creador, puedes ver esta pantalla.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }
        }
    }

    if (mostrarDialogo) {
        DialogoNombreHogar(
            nombreActual = nombreHogar,
            onCancelar = { mostrarDialogo = false },
            onGuardar = { nuevo ->
                nombreHogar = nuevo
                mostrarDialogo = false
            },
        )
    }
}

@Composable
private fun Seccion(titulo: String) {
    Text(
        titulo,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 4.dp),
    )
}

@Composable
private fun Fila(icono: ImageVector, titulo: String, detalle: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(titulo) },
        supportingContent = { Text(detalle) },
        leadingContent = { Icon(icono, contentDescription = null) },
        trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier.clickable(onClick = onClick),
    )
}

@Composable
private fun FilaInterruptor(
    icono: ImageVector,
    titulo: String,
    detalle: String,
    encendido: Boolean,
    onCambio: (Boolean) -> Unit,
) {
    ListItem(
        headlineContent = { Text(titulo) },
        supportingContent = { Text(detalle) },
        leadingContent = { Icon(icono, contentDescription = null) },
        trailingContent = {
            Switch(
                checked = encendido,
                onCheckedChange = onCambio,
                thumbContent = if (encendido) {
                    { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(SwitchDefaults.IconSize)) }
                } else {
                    null
                },
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun ConfiguracionHogarPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea {
            ConfiguracionHogarScreen(onIrPestana = {}, onMiembros = {}, onHabitaciones = {}, onVerComoMiembro = {})
        }
    }
}