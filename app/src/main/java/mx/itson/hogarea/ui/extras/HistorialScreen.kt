package mx.itson.hogarea.ui.extras

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Completada
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.EnExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedorOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

// 11 · Historial de tareas completadas (se abre desde Puntos)
@Composable
fun HistorialScreen(onAtras: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    var periodo by rememberSaveable { mutableStateOf("Esta semana") }
    // Con datos de muestra los tres filtros enseñan la misma lista; solo cambia el que está marcado
    val grupos = DatosMuestra.historial.groupBy { it.cuando }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Historial", onAtras = onAtras) },
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize()) {
            item {
                Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Esta semana", "Este mes", "Todo").forEach { opcion ->
                        FilterChip(
                            selected = periodo == opcion,
                            onClick = { periodo = opcion },
                            label = { Text(opcion) },
                        )
                    }
                }
            }
            grupos.forEach { (cuando, lista) ->
                item {
                    Text(
                        cuando,
                        style = MaterialTheme.typography.titleSmall,
                        color = cs.primary,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    )
                }
                items(lista) { completada -> FilaCompletada(completada) }
            }
        }
    }
}

@Composable
private fun FilaCompletada(c: Completada) {
    val cs = MaterialTheme.colorScheme
    val oscuro = LocalTema.current.oscuro
    ListItem(
        headlineContent = { Text(c.tarea) },
        supportingContent = { Text(c.detalle) },
        leadingContent = {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (oscuro) ExitoContenedorOscuro else ExitoContenedor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = if (oscuro) ExitoContenedor else EnExitoContenedor,
                    modifier = Modifier.size(20.dp),
                )
            }
        },
        trailingContent = {
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("+${c.puntos}", style = MaterialTheme.typography.labelLarge, color = cs.tertiary)
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    c.quienes.forEach { Avatar(it, tamano = 24.dp) }
                }
            }
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun HistorialPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { HistorialScreen(onAtras = {}) }
    }
}