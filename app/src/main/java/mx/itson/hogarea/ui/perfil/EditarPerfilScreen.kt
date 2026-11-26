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
import androidx.compose.material.icons.filled.Lock
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Sesion
import mx.itson.hogarea.datos.iconosHogar
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.componentes.CampoFecha
import mx.itson.hogarea.ui.componentes.CampoSelector
import java.time.LocalDate
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun EditarPerfilScreen(onAtras: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val yo = Sesion.usuario
    var nombre by rememberSaveable { mutableStateOf(yo.nombre) }
    var genero by rememberSaveable { mutableStateOf("Masculino") }
    var nacimiento by rememberSaveable { mutableStateOf<LocalDate?>(LocalDate.of(2004, 7, 8)) }

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
            // Uno debajo del otro: lado a lado, la fecha completa no cabe en un celular de 360 dp
            CampoSelector(
                etiqueta = "Género",
                valor = genero,
                opciones = listOf("Femenino", "Masculino", "Otro", "Prefiero no decir"),
                onElegir = { genero = it },
                modifier = Modifier.fillMaxWidth(),
            )
            CampoFecha("Nacimiento", nacimiento, { nacimiento = it }, Modifier.fillMaxWidth())
            Text(
                "Mi rol en cada hogar",
                style = MaterialTheme.typography.titleSmall,
                color = cs.primary,
                modifier = Modifier.padding(top = 8.dp),
            )
            // 5.2 · El rol se ve, pero aquí ya no se edita: lo cambia quien administra cada hogar
            DatosMuestra.hogares.filter { yo in it.miembros }.forEach { hogar ->
                OutlinedTextField(
                    value = DatosMuestra.rolDe(hogar, yo),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(hogar.nombre) },
                    leadingIcon = { Icon(iconosHogar[hogar.icono], contentDescription = null) },
                    trailingIcon = { Icon(Icons.Filled.Lock, contentDescription = "No se puede editar") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Text(
                "Tu rol lo asigna quien administra cada hogar, desde el detalle de miembro.",
                style = MaterialTheme.typography.bodyMedium,
                color = cs.onSurfaceVariant,
            )
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