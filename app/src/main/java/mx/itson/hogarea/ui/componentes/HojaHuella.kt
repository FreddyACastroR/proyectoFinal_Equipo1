package mx.itson.hogarea.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// En este avance es solo visual:
// tocar el círculo cuenta como huella correcta (onSensor).
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HojaHuella(
    titulo: String,
    texto: String,
    onCancelar: () -> Unit,
    onSensor: () -> Unit,
    textoCancelar: String = "Cancelar",
) {
    ModalBottomSheet(onDismissRequest = onCancelar) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(titulo, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
            Text(
                texto,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Box(
                Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable(onClick = onSensor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Fingerprint,
                    contentDescription = "Sensor de huella",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(40.dp),
                )
            }
            Text("Toca el sensor de huella", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = onCancelar) { Text(textoCancelar) }
        }
    }
}