package mx.itson.hogarea.ui.estadisticas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.componentes.BarraInferior
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasScreen(onIrPestana: (String) -> Unit) {
    var pestana by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            Column {
                BarraSuperior("Estadísticas") {
                    IconButton(onClick = { }) { Icon(Icons.Filled.Share, contentDescription = "Compartir") }
                }
                PrimaryTabRow(selectedTabIndex = pestana, containerColor = Color.Transparent) {
                    listOf("Por hogar", "Todos mis hogares").forEachIndexed { i, texto ->
                        Tab(selected = pestana == i, onClick = { pestana = i }, text = { Text(texto) })
                    }
                }
            }
        },
        bottomBar = { BarraInferior(Rutas.ESTADISTICAS, onIrPestana) },
    ) { padding ->
        val contenido = Modifier.padding(padding).padding(top = 12.dp).fillMaxSize()
        if (pestana == 0) PorHogar(contenido) else TodosMisHogares(contenido)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EstadisticasPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { EstadisticasScreen(onIrPestana = {}) }
    }
}