package mx.itson.hogarea.ui.componentes

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import mx.itson.hogarea.ui.navegacion.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    titulo: String,
    onAtras: (() -> Unit)? = null,
    acciones: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(titulo) },
        navigationIcon = {
            if (onAtras != null) {
                IconButton(onClick = onAtras) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                }
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

private class Pestana(val ruta: String, val texto: String, val icono: ImageVector)

private val pestanas = listOf(
    Pestana(Rutas.MIS_TAREAS, "Tareas", Icons.Filled.Checklist),
    Pestana(Rutas.ESTADISTICAS, "Estadísticas", Icons.Filled.BarChart),
    Pestana(Rutas.CONFIG_HOGAR, "Hogar", Icons.Filled.Home),
    Pestana(Rutas.PERFIL, "Perfil", Icons.Filled.Person),
)

@Composable
fun BarraInferior(rutaActual: String, onIr: (String) -> Unit) {
    NavigationBar {
        pestanas.forEach { p ->
            NavigationBarItem(
                selected = rutaActual == p.ruta,
                onClick = { if (rutaActual != p.ruta) onIr(p.ruta) },
                icon = { Icon(p.icono, contentDescription = null) },
                label = { Text(p.texto) },
            )
        }
    }
}