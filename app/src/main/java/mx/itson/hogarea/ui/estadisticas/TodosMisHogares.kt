package mx.itson.hogarea.ui.estadisticas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.R
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.theme.Exito
import mx.itson.hogarea.ui.theme.ExitoOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

@Composable
fun TodosMisHogares(modifier: Modifier = Modifier) {
    val cs = MaterialTheme.colorScheme
    val verde = if (LocalTema.current.oscuro) ExitoOscuro else Exito
    var hogar by rememberSaveable { mutableStateOf("Todos") }
    var mes by rememberSaveable { mutableStateOf(meses.first()) }

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Filtro(Icons.Filled.Home, hogar, listOf("Todos") + DatosMuestra.hogares.map { it.nombre }) { hogar = it }
            Filtro(Icons.Filled.CalendarMonth, mes, meses) { mes = it }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Indicador("2", "Hogares donde participas", cs.primary, Modifier.weight(1f))
            Indicador("58", "Completadas en total", verde, Modifier.weight(1f))
            Indicador("9%", "No completadas", cs.error, Modifier.weight(1f))
        }
        TarjetaGrafica("Completadas por semana", "$mes · todos tus hogares", R.drawable.grafica_por_semana)
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Cumplimiento por hogar", style = MaterialTheme.typography.titleSmall)
                Cumplimiento("Casa Nainari", "42 completadas · 88%", 0.88f)
                Cumplimiento("Depa Obregón", "16 completadas · 94%", 0.94f)
            }
        }
    }
}

@Composable
private fun Cumplimiento(hogar: String, detalle: String, avance: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row {
            Text(hogar, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Text(detalle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        LinearProgressIndicator(progress = { avance }, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TodosMisHogaresPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { TodosMisHogares(Modifier.fillMaxSize()) }
    }
}