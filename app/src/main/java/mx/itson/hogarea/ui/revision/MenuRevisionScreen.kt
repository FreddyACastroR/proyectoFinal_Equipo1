package mx.itson.hogarea.ui.revision

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.navegacion.Rutas
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

// Menú de la rama demo/revision. Las pantallas de dentro de la app se abren encima
// de Mis tareas, como queda la pila después de iniciar sesión; así Guardar, Atrás
// y las pestañas regresan a donde regresarían en la app real.

private class Entrada(val codigos: String, val nombre: String, val quien: List<Persona>, val camino: List<String>)

// listo = true cuando el PR del caso ya está unido en frontend
private class Caso(val titulo: String, val equipo: String, val listo: Boolean, val entradas: List<Entrada>)

// La primera tarea pendiente de Casa Nainari, por si en la revisión ya se completó o eliminó la 1
private fun tareaPendiente() = DatosMuestra.tareas
    .firstOrNull { it.hogar == DatosMuestra.casaNainari.nombre && it.estado == Estado.PENDIENTE }?.id ?: 1

private fun casos() = listOf(
    Caso(
        "Acceso", "En pareja · André y Rubén", listo = false,
        listOf(
            Entrada("01, 01b y 01c", "Iniciar sesión", listOf(Persona.ANDRE), listOf(Rutas.LOGIN)),
            Entrada("02", "Recuperar contraseña", listOf(Persona.RUBEN), listOf(Rutas.RECUPERAR)),
            Entrada("03", "Registro", listOf(Persona.RUBEN), listOf(Rutas.REGISTRO)),
        ),
    ),
    Caso(
        "Crear o unirse a un hogar", "En pareja · André y Ángel", listo = false,
        listOf(
            Entrada("04", "Crear o unirse a un hogar", listOf(Persona.ANDRE), listOf(Rutas.BIENVENIDA)),
            Entrada("04b", "Crear hogar", listOf(Persona.ANGEL), listOf(Rutas.CREAR_HOGAR)),
            Entrada("04c", "Hogar creado", listOf(Persona.ANGEL), listOf(Rutas.HOGAR_CREADO)),
            Entrada("04d y 04e", "Unirme con código", listOf(Persona.ANDRE), listOf(Rutas.UNIRSE)),
        ),
    ),
    Caso(
        "Mis tareas", "En pareja · Rubén y Ángel", listo = false,
        listOf(
            Entrada("05", "Mis tareas", listOf(Persona.RUBEN, Persona.ANGEL), listOf(Rutas.MIS_TAREAS)),
            Entrada("05b", "Tareas de un hogar, sin edición", listOf(Persona.ANGEL), listOf(Rutas.MIS_TAREAS, Rutas.TAREAS_HOGAR)),
        ),
    ),
    Caso(
        "Crear y editar tareas", "Caso individual de Rubén", listo = true,
        listOf(
            Entrada("06a, 06e y 06b", "Nueva tarea", listOf(Persona.RUBEN), listOf(Rutas.MIS_TAREAS, Rutas.NUEVA_TAREA)),
            Entrada(
                "06c y 06d", "Editar tarea", listOf(Persona.RUBEN),
                listOf(Rutas.MIS_TAREAS, Rutas.detalle(tareaPendiente()), Rutas.EDITAR_TAREA),
            ),
        ),
    ),
    Caso(
        "Detalle de tarea", "Caso individual de Ángel", listo = true,
        listOf(
            Entrada("07, 07b y 07c", "Completar, pausar y eliminar", listOf(Persona.ANGEL), listOf(Rutas.MIS_TAREAS, Rutas.detalle(tareaPendiente()))),
            Entrada("07d", "Detalle, solo lectura", listOf(Persona.ANGEL), listOf(Rutas.MIS_TAREAS, Rutas.detalleLectura(2))),
        ),
    ),
    Caso(
        "Configurar el hogar", "Caso individual de Freddy", listo = true,
        listOf(
            Entrada("08 y 08b a 08h", "Configuración del hogar", listOf(Persona.FREDDY), listOf(Rutas.MIS_TAREAS, Rutas.CONFIG_HOGAR)),
            Entrada(
                "08i", "Hogar, vista de miembro", listOf(Persona.FREDDY),
                listOf(Rutas.MIS_TAREAS, Rutas.CONFIG_HOGAR, Rutas.HOGAR_MIEMBRO),
            ),
        ),
    ),
    Caso(
        "Estadísticas", "Caso individual de André", listo = false,
        listOf(Entrada("09 y 09b", "Estadísticas", listOf(Persona.ANDRE), listOf(Rutas.MIS_TAREAS, Rutas.ESTADISTICAS))),
    ),
    Caso(
        "Perfil y modo oscuro", "En pareja · Rubén y André", listo = false,
        listOf(
            Entrada("10", "Perfil", listOf(Persona.RUBEN), listOf(Rutas.MIS_TAREAS, Rutas.PERFIL)),
            Entrada("10b", "Editar perfil", listOf(Persona.ANDRE), listOf(Rutas.MIS_TAREAS, Rutas.PERFIL, Rutas.EDITAR_PERFIL)),
        ),
    ),
    Caso(
        "Extras", "En pareja · Ángel y André", listo = false,
        listOf(
            Entrada("11", "Historial de completadas", listOf(Persona.ANGEL), listOf(Rutas.MIS_TAREAS, Rutas.HISTORIAL)),
            Entrada("12", "Puntos y recompensas", listOf(Persona.ANDRE), listOf(Rutas.MIS_TAREAS, Rutas.PUNTOS)),
            Entrada("13 y 13b", "Notificaciones", listOf(Persona.ANGEL), listOf(Rutas.MIS_TAREAS, Rutas.NOTIFICACIONES)),
        ),
    ),
)

@Composable
fun MenuRevisionScreen(onAbrir: (List<String>) -> Unit) {
    val cs = MaterialTheme.colorScheme
    val tema = LocalTema.current
    val (listos, pendientes) = casos().partition { it.listo }

    Scaffold(
        containerColor = Color.Transparent, // el fondo lo pone FondoHogarea
        topBar = { BarraSuperior("Revisión del avance") },
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
                "Cada caso de uso se abre como en la app: encima de Mis tareas, que es la pantalla de inicio después de iniciar sesión.",
                style = MaterialTheme.typography.bodyMedium,
                color = cs.onSurfaceVariant,
            )
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                ListItem(
                    headlineContent = { Text("Modo oscuro") },
                    leadingContent = { Icon(Icons.Filled.DarkMode, contentDescription = null) },
                    trailingContent = { Switch(checked = tema.oscuro, onCheckedChange = null) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier.toggleable(value = tema.oscuro, role = Role.Switch, onValueChange = tema.cambiar),
                )
            }

            Seccion("Listo para revisar")
            listos.forEach { TarjetaCaso(it, onAbrir) }

            Seccion("En construcción")
            Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
                pendientes.forEach { caso ->
                    ListItem(
                        headlineContent = { Text(caso.titulo) },
                        supportingContent = { Text("${caso.equipo}\n${caso.entradas.joinToString(" · ") { it.codigos }}") },
                        leadingContent = { Avatares(caso.entradas.flatMap { it.quien }.distinct()) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun Seccion(titulo: String) {
    Text(
        titulo,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp),
    )
}

@Composable
private fun TarjetaCaso(caso: Caso, onAbrir: (List<String>) -> Unit) {
    val cs = MaterialTheme.colorScheme
    Card(colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLow)) {
        Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 14.dp)) {
            Text(caso.titulo, style = MaterialTheme.typography.titleMedium)
            Text(caso.equipo, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant)
        }
        caso.entradas.forEach { entrada ->
            ListItem(
                headlineContent = { Text(entrada.nombre) },
                supportingContent = { Text(entrada.codigos) },
                leadingContent = { Avatares(entrada.quien) },
                trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null) },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                modifier = Modifier.clickable { onAbrir(entrada.camino) },
            )
        }
    }
}

@Composable
private fun Avatares(personas: List<Persona>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        personas.forEach { Avatar(it) }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun MenuRevisionPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { MenuRevisionScreen(onAbrir = {}) }
    }
}
