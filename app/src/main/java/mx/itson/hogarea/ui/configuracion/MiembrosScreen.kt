package mx.itson.hogarea.ui.configuracion

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun MiembrosScreen(onAtras: () -> Unit, onMiembro: () -> Unit) {
    val hogar = DatosMuestra.casaNainari
    val cs = MaterialTheme.colorScheme
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Miembros de ${hogar.nombre}", onAtras = onAtras) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${hogar.miembros.size} integrantes",
                    style = MaterialTheme.typography.titleSmall,
                    color = cs.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = { }) {
                    Icon(Icons.Filled.PersonAdd, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Invitar")
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                hogar.miembros.forEach { persona ->
                    val esCreador = persona == hogar.creador
                    ListItem(
                        headlineContent = { Text(if (esCreador) "${persona.nombre} (tú)" else persona.nombre) },
                        supportingContent = { Text(if (esCreador) "Roomie · Creador del hogar" else "Roomie · Miembro") },
                        leadingContent = { Avatar(persona, tamano = 40.dp) },
                        trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier.clickable(onClick = onMiembro),
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                Column(
                    Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("Código de invitación", style = MaterialTheme.typography.labelLarge, color = cs.onSurfaceVariant)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(hogar.codigo, style = MaterialTheme.typography.headlineMedium, color = cs.primary)
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = "Copiar código", tint = cs.primary)
                        }
                    }
                    Text("Vence en 7 días.", style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
                }
            }
            Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Compartir código de invitación")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = cs.onSurfaceVariant)
                Text(
                    "Los permisos de edición se cambian en Configuración del hogar.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MiembrosPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { MiembrosScreen(onAtras = {}, onMiembro = {}) }
    }
}