package mx.itson.hogarea.ui.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

private val roles = listOf("Mamá", "Papá", "Hijo o hija", "Roomie", "Otro")

@Composable
fun EditarPerfilScreen(onAtras: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val yo = Persona.ANDRE
    var nombre by rememberSaveable { mutableStateOf(yo.nombre) }
    var genero by rememberSaveable { mutableStateOf("Masculino") }
    var nacimiento by rememberSaveable { mutableStateOf("08/07/2004") }
    // Un rol por hogar: nombre del hogar → rol
    val rolPorHogar = remember { mutableStateMapOf("Casa Nainari" to "Roomie", "Depa Obregón" to "Roomie") }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Editar perfil", onAtras = onAtras) },
        bottomBar = {
            Row(
                Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(onClick = onAtras, modifier = Modifier.weight(1f).height(48.dp)) { Text("Cancelar") }
                Button(onClick = onAtras, modifier = Modifier.weight(1f).height(48.dp)) { Text("Guardar cambios") }
            }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Avatar(yo, tamano = 96.dp)
                TextButton(onClick = { }) { Text("Cambiar foto") }
            }
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = yo.correo,
                onValueChange = {},
                enabled = false,
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                supportingText = { Text("El correo no se puede cambiar.") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoMenu("Género", genero, listOf("Femenino", "Masculino", "Otro", "Prefiero no decir"), { genero = it }, Modifier.weight(1f))
                OutlinedTextField(
                    value = nacimiento,
                    onValueChange = { nacimiento = it },
                    label = { Text("Nacimiento") },
                    trailingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
            }
            Text(
                "Mi rol en cada hogar",
                style = MaterialTheme.typography.titleSmall,
                color = cs.primary,
                modifier = Modifier.padding(top = 8.dp),
            )
            DatosMuestra.hogares.forEach { hogar ->
                CampoMenu(
                    etiqueta = hogar.nombre,
                    valor = rolPorHogar[hogar.nombre] ?: "Roomie",
                    opciones = roles,
                    onElegir = { rolPorHogar[hogar.nombre] = it },
                    modifier = Modifier.fillMaxWidth(),
                    icono = Icons.Filled.Home,
                )
            }
            Text(
                "Tu rol solo lo ven los demás miembros; no cambia tus permisos.",
                style = MaterialTheme.typography.bodySmall,
                color = cs.onSurfaceVariant,
            )
        }
    }
}

// Campo de solo lectura que abre un menú con la flecha
@Composable
private fun CampoMenu(
    etiqueta: String,
    valor: String,
    opciones: List<String>,
    onElegir: (String) -> Unit,
    modifier: Modifier,
    icono: ImageVector? = null,
) {
    var abierto by remember { mutableStateOf(false) }
    Box(modifier) {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            leadingIcon = if (icono != null) {
                { Icon(icono, contentDescription = null) }
            } else {
                null
            },
            trailingIcon = {
                IconButton(onClick = { abierto = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Elegir $etiqueta")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onElegir(opcion)
                        abierto = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun EditarPerfilPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { EditarPerfilScreen(onAtras = {}) }
    }
}