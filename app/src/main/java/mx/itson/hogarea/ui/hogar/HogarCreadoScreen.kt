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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.theme.EnExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedorOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

@Composable
fun HogarCreadoScreen(onIrATareas: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val oscuro = LocalTema.current.oscuro
    val hogar = DatosMuestra.casaNainari

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            Button(
                onClick = onIrATareas,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(48.dp),
            ) { Text("Ir a mis tareas") }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(Modifier.height(24.dp))
            Box(
                Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(if (oscuro) ExitoContenedorOscuro else ExitoContenedor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = if (oscuro) ExitoContenedor else EnExitoContenedor,
                    modifier = Modifier.size(40.dp),
                )
            }
            Text("¡Listo! Tu hogar está creado", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
            Text(
                "Comparte este código para que los demás se unan a ${hogar.nombre}.",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLowest)) {
                Column(
                    Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text("Código de invitación", style = MaterialTheme.typography.labelLarge, color = cs.onSurfaceVariant)
                    Text(hogar.codigo, style = MaterialTheme.typography.displaySmall, color = cs.primary)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FilledTonalButton(onClick = { }) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Copiar")
                        }
                        FilledTonalButton(onClick = { }) {
                            Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Compartir")
                        }
                    }
                }
            }
            Text(
                "El código vence en 7 días. Puedes generar otro en Configuración del hogar.",
                style = MaterialTheme.typography.bodySmall,
                color = cs.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HogarCreadoPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { HogarCreadoScreen(onIrATareas = {}) }
    }
}