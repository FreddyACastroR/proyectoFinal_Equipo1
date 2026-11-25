package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.iconosHogar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraInferior
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun HogarMiembroScreen(onIrPestana: (String) -> Unit, onSalir: () -> Unit) {
    val hogar = Sesion.hogarActivo
    val yo = Sesion.usuario
    val cs = MaterialTheme.colorScheme
    var menuAbierto by remember { mutableStateOf(false) }
    var confirmarSalir by remember { mutableStateOf(false) } // 08k
    val sinFondo = ListItemDefaults.colors(containerColor = Color.Transparent)

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Hogar") },
        bottomBar = { BarraInferior(Rutas.CONFIG_HOGAR, onIrPestana) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Hogar:", style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
                Box {
                    AssistChip(
                        onClick = { menuAbierto = true },
                        label = { Text(hogar.nombre) },
                        leadingIcon = {
                            Icon(iconosHogar[hogar.icono], contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize))
                        },
                        trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
                        colors = AssistChipDefaults.assistChipColors(containerColor = cs.secondaryContainer),
                        border = null,
                    )
                    DropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
                        DatosMuestra.hogares.filter { yo in it.miembros }.forEach { h ->
                            DropdownMenuItem(
                                text = { Text(h.nombre) },
                                onClick = {
                                    menuAbierto = false
                                    Sesion.hogarActivo = h
                                },
                            )
                        }
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = cs.tertiaryContainer, contentColor = cs.onTertiaryContainer),
            ) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                    Text(
                        "Eres miembro de este hogar. Solo ${hogar.creador.nombre}, quien lo administra, puede cambiar la configuración y los roles.",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                ListItem(
                    headlineContent = { Text(hogar.nombre) },
                    supportingContent = { Text("Creado por ${hogar.creador.nombre} · ${hogar.miembros.size} miembros") },
                    leadingContent = { Icon(iconosHogar[hogar.icono], contentDescription = null) },
                    colors = sinFondo,
                )
                ListItem(
                    headlineContent = { Text("Tu rol: ${DatosMuestra.rolDe(hogar, yo)}") },
                    supportingContent = {
                        Text(
                            if (hogar.miembrosPuedenEditar) "Ver, crear y editar tareas, y completar las tuyas"
                            else "Ver tareas y completar las tuyas",
                        )
                    },
                    leadingContent = { Icon(Icons.Filled.Person, contentDescription = null) },
                    colors = sinFondo,
                )
            }
            Text("Habitantes (${hogar.miembros.size})", style = MaterialTheme.typography.titleSmall, color = cs.primary)
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                hogar.miembros.forEach { persona ->
                    val nombre = persona.nombre.substringBefore(" ")
                    val rol = DatosMuestra.rolDe(hogar, persona)
                    ListItem(
                        headlineContent = { Text(if (persona == yo) "$nombre (tú)" else nombre) },
                        supportingContent = { Text(if (persona == hogar.creador) "$rol · Administra el hogar" else "$rol · Miembro") },
                        leadingContent = { Avatar(persona, tamano = 40.dp) },
                        colors = sinFondo,
                    )
                }
            }
            OutlinedButton(
                onClick = { confirmarSalir = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = cs.error),
                border = BorderStroke(1.dp, cs.error),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Salir del hogar")
            }
            Spacer(Modifier.height(8.dp))
        }
    }

    if (confirmarSalir) {
        AlertDialog(
            onDismissRequest = { confirmarSalir = false },
            title = { Text("¿Salir de ${hogar.nombre}?") },
            text = { Text("Dejarás de ver sus tareas. Podrás volver a unirte con el código de invitación.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarSalir = false
                    DatosMuestra.quitarDelHogar(hogar, yo)
                    onSalir()
                }) { Text("Salir", color = cs.error) }
            },
            dismissButton = { TextButton(onClick = { confirmarSalir = false }) { Text("Cancelar") } },
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HogarMiembroPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { HogarMiembroScreen(onIrPestana = {}, onSalir = {}) }
    }
}