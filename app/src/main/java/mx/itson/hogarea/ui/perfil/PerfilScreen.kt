package mx.itson.hogarea.ui.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun PerfilScreen(
    onIrPestana: (String) -> Unit,
    onEditar: () -> Unit,
    onSalir: () -> Unit,
    onPuntos: () -> Unit,
) {
    val cs = MaterialTheme.colorScheme
    val yo = Persona.ANDRE
    val puntos = DatosMuestra.puntos.first { it.first == yo }.second
    val completadas = DatosMuestra.completadasDelMes.first { it.first == yo }.second
    var recordatorios by rememberSaveable { mutableStateOf(true) }
    var huella by rememberSaveable { mutableStateOf(true) }
    var oscuro by rememberSaveable { mutableStateOf(false) } // en el paso 2 se cambia por el tema de la app

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            BarraSuperior("Perfil") {
                IconButton(onClick = onEditar) { Icon(Icons.Filled.Edit, contentDescription = "Editar perfil") }
                IconButton(onClick = onSalir) { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión") }
            }
        },
        bottomBar = { BarraInferior(Rutas.PERFIL, onIrPestana) },
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
            Box {
                Avatar(yo, tamano = 96.dp)
                FilledIconButton(
                    onClick = onEditar,
                    modifier = Modifier.size(32.dp).align(Alignment.BottomEnd),
                ) {
                    Icon(Icons.Filled.PhotoCamera, contentDescription = "Cambiar foto", modifier = Modifier.size(16.dp))
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(yo.nombre, style = MaterialTheme.typography.headlineSmall)
                Text(yo.correo, style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant)
            }
            AssistChip(
                onClick = onPuntos,
                label = { Text("$puntos puntos · $completadas tareas este mes") },
                leadingIcon = { Icon(Icons.Filled.Star, contentDescription = null, modifier = Modifier.size(AssistChipDefaults.IconSize)) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = cs.tertiaryContainer,
                    labelColor = cs.onTertiaryContainer,
                    leadingIconContentColor = cs.onTertiaryContainer,
                ),
                border = null,
            )

            Titulo("Mis hogares y mi rol")
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                DatosMuestra.hogares.forEach { hogar ->
                    val permiso = if (hogar.miembrosPuedenEditar) "puede editar" else "solo ver"
                    ListItem(
                        headlineContent = { Text(hogar.nombre) },
                        supportingContent = { Text("Roomie · $permiso") },
                        leadingContent = { Icon(Icons.Filled.Home, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }

            Titulo("Preferencias")
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                FilaInterruptor(Icons.Filled.Notifications, "Recordatorios", "Avisarme cuando me toque una tarea", recordatorios) {
                    recordatorios = it
                }
                FilaInterruptor(Icons.Filled.Fingerprint, "Entrar con huella", "Sin escribir la contraseña", huella) {
                    huella = it
                }
                FilaInterruptor(Icons.Filled.DarkMode, "Tema oscuro", "Colores oscuros en toda la app", oscuro) {
                    oscuro = it
                }
            }
        }
    }
}

@Composable
private fun Titulo(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
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
private fun PerfilPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { PerfilScreen(onIrPestana = {}, onEditar = {}, onSalir = {}, onPuntos = {}) }
    }
}