package mx.itson.hogarea.ui.extras

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Notificacion
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.EnExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedorOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

// 13 · Notificaciones (se abre con la campana de Mis tareas)
@Composable
fun NotificacionesScreen(onAtras: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val grupos = DatosMuestra.notificaciones.groupBy { it.cuando } // "Hoy" y "Ayer"

    // 13b · Desde Android 13 hay que pedir permiso antes de mostrar notificaciones
    val contexto = LocalContext.current
    val pedirPermiso = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) mostrarRecordatorio(contexto)
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Notificaciones", onAtras = onAtras) },
    ) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize()) {
            grupos.forEach { (cuando, lista) ->
                item {
                    Text(
                        cuando,
                        style = MaterialTheme.typography.titleSmall,
                        color = cs.primary,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                    )
                }
                items(lista) { notificacion -> FilaNotificacion(notificacion) }
            }
            item {
                Text(
                    "Puedes apagar los recordatorios en tu Perfil.",
                    style = MaterialTheme.typography.bodySmall,
                    color = cs.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp),
                )
            }
            item {
                FilledTonalButton(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            mostrarRecordatorio(contexto)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                ) {
                    Icon(Icons.Filled.Notifications, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text("Probar recordatorio en el teléfono", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun FilaNotificacion(n: Notificacion) {
    val cs = MaterialTheme.colorScheme
    val oscuro = LocalTema.current.oscuro
    // El ícono y su color dependen de qué avisa la notificación
    val (icono: ImageVector, fondo: Color, color: Color) = when {
        n.titulo.startsWith("Te toca") -> Triple(Icons.Filled.Checklist, cs.primaryContainer, cs.onPrimaryContainer)
        n.titulo.startsWith("Recordatorio") -> Triple(Icons.Filled.Alarm, cs.tertiaryContainer, cs.onTertiaryContainer)
        "completó" in n.titulo -> Triple(
            Icons.Filled.Check,
            if (oscuro) ExitoContenedorOscuro else ExitoContenedor,
            if (oscuro) ExitoContenedor else EnExitoContenedor,
        )
        "pausó" in n.titulo -> Triple(Icons.Filled.Pause, cs.secondaryContainer, cs.onSecondaryContainer)
        n.titulo.startsWith("Ganaste") -> Triple(Icons.Filled.Star, cs.tertiaryContainer, cs.onTertiaryContainer)
        else -> Triple(Icons.Filled.PersonAdd, cs.secondaryContainer, cs.onSecondaryContainer)
    }
    ListItem(
        headlineContent = { Text(n.titulo) },
        supportingContent = { Text(n.detalle) },
        leadingContent = {
            Box(Modifier.size(40.dp).clip(CircleShape).background(fondo), contentAlignment = Alignment.Center) {
                Icon(icono, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
        },
        trailingContent = { Text(n.hora, style = MaterialTheme.typography.labelMedium) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun NotificacionesPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { NotificacionesScreen(onAtras = {}) }
    }
}