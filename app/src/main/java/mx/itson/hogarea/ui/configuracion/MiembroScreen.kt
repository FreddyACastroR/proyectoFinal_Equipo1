package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.esAdmin
import mx.itson.hogarea.datos.rolesFamiliares
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun MiembroScreen(persona: Persona, onAtras: () -> Unit) {
    val hogar = Sesion.hogarActivo
    val cs = MaterialTheme.colorScheme
    val administro = esAdmin(hogar)
    val esCreador = persona == hogar.creador
    val rol = DatosMuestra.rolDe(hogar, persona)
    val completadas = DatosMuestra.completadasDelMes.firstOrNull { it.first == persona }?.second ?: 0
    var cambiarRol by remember { mutableStateOf(false) }   // 08e-b
    var confirmarQuitar by remember { mutableStateOf(false) } // 08k

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Detalle de miembro", onAtras = onAtras) },
        bottomBar = {
            if (administro && !esCreador) {
                Button(
                    onClick = { confirmarQuitar = true },
                    colors = ButtonDefaults.buttonColors(containerColor = cs.error, contentColor = cs.onError),
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                        .height(48.dp),
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Quitar del hogar")
                }
            }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Avatar(persona, tamano = 96.dp)
            Text(persona.nombre, style = MaterialTheme.typography.headlineSmall)
            Text(
                "$rol · ${if (esCreador) "Administra" else "Miembro de"} ${hogar.nombre}",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
            )
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                Dato(Icons.Filled.Email, "Correo", persona.correo)
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Dato(Icons.Filled.Person, "Rol en el hogar", rol, Modifier.weight(1f))
                    if (administro) {
                        TextButton(onClick = { cambiarRol = true }, modifier = Modifier.padding(end = 8.dp)) { Text("Cambiar") }
                    }
                }
                Dato(Icons.Filled.Event, "Se unió", "15 oct 2026")
                Dato(Icons.Filled.CheckCircle, "Este mes", "$completadas tareas completadas")
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Filled.Visibility, contentDescription = null, tint = cs.onSurfaceVariant)
                    Text(
                        if (esCreador) {
                            "Administra el hogar: crea, edita y elimina tareas, habitaciones y roles."
                        } else if (hogar.miembrosPuedenEditar) {
                            "Puede ver, crear y editar tareas, y completar las que le asignen."
                        } else {
                            "Puede ver las tareas y completar las que le asignen."
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = cs.onSurfaceVariant,
                    )
                }
            }
        }
    }

    if (cambiarRol) {
        var elegido by remember { mutableStateOf(rol) }
        AlertDialog(
            onDismissRequest = { cambiarRol = false },
            title = { Text("Rol de ${persona.nombre.substringBefore(" ")}") },
            text = {
                Column {
                    rolesFamiliares.forEach { opcion ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(selected = elegido == opcion, role = Role.RadioButton) { elegido = opcion }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(selected = elegido == opcion, onClick = null)
                            Spacer(Modifier.width(12.dp))
                            Text(opcion, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    DatosMuestra.cambiarRol(hogar, persona, elegido)
                    cambiarRol = false
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { cambiarRol = false }) { Text("Cancelar") } },
        )
    }

    if (confirmarQuitar) {
        AlertDialog(
            onDismissRequest = { confirmarQuitar = false },
            title = { Text("¿Quitar a ${persona.nombre.substringBefore(" ")}?") },
            text = { Text("Dejará de ver ${hogar.nombre} y sus tareas. Podrá volver a unirse con el código de invitación.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarQuitar = false
                    DatosMuestra.quitarDelHogar(hogar, persona)
                    onAtras()
                }) { Text("Quitar", color = cs.error) }
            },
            dismissButton = { TextButton(onClick = { confirmarQuitar = false }) { Text("Cancelar") } },
        )
    }
}

@Composable
private fun Dato(icono: ImageVector, etiqueta: String, valor: String, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(16.dp))
        // 4.1 · Letra más grande en los datos del miembro
        Text(etiqueta, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Text(valor, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MiembroPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { MiembroScreen(persona = Persona.FREDDY, onAtras = {}) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MiembroOscuroPreview() {
    HogareaTheme(oscuro = true, onCambiarTema = {}) {
        FondoHogarea { MiembroScreen(persona = Persona.FREDDY, onAtras = {}) }
    }
}