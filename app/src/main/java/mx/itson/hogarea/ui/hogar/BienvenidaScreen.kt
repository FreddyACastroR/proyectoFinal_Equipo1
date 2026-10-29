package mx.itson.hogarea.ui.hogar

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

// 04 · Crear un hogar o unirse con código (después del registro)
@Composable
fun BienvenidaScreen(onCrear: () -> Unit, onUnirse: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    Scaffold(containerColor = Color.Transparent) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(Modifier.height(24.dp))
            Text("¡Hola, ${Persona.ANGEL.nombre}!", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Para empezar, crea tu hogar o únete a uno con el código que te compartieron.",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
            )
            Spacer(Modifier.height(8.dp))
            Opcion(
                Icons.Filled.Home, cs.primaryContainer, cs.onPrimaryContainer, "Crear un hogar",
                "Serás el creador: decides las habitaciones y quién puede editar las tareas.", onCrear,
            )
            Opcion(
                Icons.Filled.Key, cs.tertiaryContainer, cs.onTertiaryContainer, "Unirme con un código",
                "Pídele el código de invitación a quien creó el hogar.", onUnirse,
            )
            Spacer(Modifier.weight(1f))
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Filled.Group, contentDescription = null, tint = cs.onSurfaceVariant)
                    Text(
                        "Puedes participar en varios hogares a la vez, por ejemplo tu casa y el depa donde vives entre semana.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = cs.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun Opcion(icono: ImageVector, fondo: Color, color: Color, titulo: String, texto: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).clip(CircleShape).background(fondo), contentAlignment = Alignment.Center) {
                Icon(icono, contentDescription = null, tint = color)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(titulo, style = MaterialTheme.typography.titleMedium)
                Text(texto, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun BienvenidaPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { BienvenidaScreen(onCrear = {}, onUnirse = {}) }
    }
}