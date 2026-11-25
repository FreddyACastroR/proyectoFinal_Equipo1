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
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.ui.componentes.compartirCodigo
import mx.itson.hogarea.ui.componentes.copiarCodigo
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun MiembrosScreen(onAtras: () -> Unit, onMiembro: (Persona) -> Unit) {
    val hogar = Sesion.hogarActivo
    val cs = MaterialTheme.colorScheme
    val contexto = LocalContext.current
    var copiado by remember { mutableStateOf(false) }
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
            Text(
                "${hogar.miembros.size} integrantes",
                style = MaterialTheme.typography.titleSmall,
                color = cs.onSurfaceVariant,
            )
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                hogar.miembros.forEach { persona ->
                    val esCreador = persona == hogar.creador
                    val rol = DatosMuestra.rolDe(hogar, persona)
                    ListItem(
                        headlineContent = {
                            Text(
                                if (persona == Sesion.usuario) "${persona.nombre} (tú)" else persona.nombre,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        },
                        supportingContent = {
                            Text(
                                if (esCreador) "$rol · Administra el hogar" else "$rol · Miembro",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        },
                        leadingContent = { Avatar(persona, tamano = 48.dp) },
                        trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier.clickable { onMiembro(persona) },
                    )
                }
            }
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cs.primaryContainer, contentColor = cs.onPrimaryContainer),
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Código de invitación", style = MaterialTheme.typography.titleSmall)
                    Text(
                        hogar.codigo,
                        style = MaterialTheme.typography.displaySmall,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                    )
                    TextButton(onClick = {
                        copiarCodigo(contexto, hogar.codigo)
                        copiado = true
                    }) {
                        Icon(
                            if (copiado) Icons.Filled.Check else Icons.Filled.ContentCopy,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(if (copiado) "Copiado" else "Copiar código")
                    }
                    Text("Vence en 7 días.", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Button(
                onClick = { compartirCodigo(contexto, hogar.nombre, hogar.codigo) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Compartir código de invitación")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = cs.onSurfaceVariant)
                Text(
                    "Los permisos de edición se cambian en Configuración del hogar.",
                    style = MaterialTheme.typography.bodyMedium,
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