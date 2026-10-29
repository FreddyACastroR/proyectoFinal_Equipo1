package mx.itson.hogarea.ui.acceso

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun RegistroScreen(onAtras: () -> Unit, onCrearCuenta: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    var nombre by rememberSaveable { mutableStateOf("Ángel") }
    var genero by rememberSaveable { mutableStateOf("Masculino") }
    var nacimiento by rememberSaveable { mutableStateOf("12/03/2004") }
    var correo by rememberSaveable { mutableStateOf("angel@correo.com") }
    var contrasena by rememberSaveable { mutableStateOf("hogarea123") }
    var confirmar by rememberSaveable { mutableStateOf("hogarea123") }
    var terminos by rememberSaveable { mutableStateOf(true) }
    val coinciden = contrasena == confirmar

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Crear cuenta", onAtras = onAtras) },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                "Crea tu cuenta para organizar las tareas de tu hogar.",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
            )
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoGenero(genero, { genero = it }, Modifier.weight(1f))
                OutlinedTextField(
                    value = nacimiento,
                    onValueChange = { nacimiento = it },
                    label = { Text("Nacimiento") },
                    trailingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
            }
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                supportingText = { Text("Mínimo 8 caracteres e incluye un número") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = confirmar,
                onValueChange = { confirmar = it },
                label = { Text("Confirmar contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                isError = !coinciden,
                supportingText = if (!coinciden) {
                    { Text("Las contraseñas no coinciden") }
                } else {
                    null
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Row(
                Modifier.fillMaxWidth().clickable { terminos = !terminos },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = terminos, onCheckedChange = { terminos = it })
                Text("Acepto los términos y el aviso de privacidad", style = MaterialTheme.typography.bodyMedium)
            }
            Button(
                onClick = onCrearCuenta,
                enabled = terminos && coinciden && nombre.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) { Text("Crear cuenta") }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("¿Ya tienes cuenta?", color = cs.onSurfaceVariant)
                TextButton(onClick = onAtras) { Text("Inicia sesión") }
            }
        }
    }
}
@Composable
private fun CampoGenero(valor: String, onElegir: (String) -> Unit, modifier: Modifier) {
    var abierto by remember { mutableStateOf(false) }
    Box(modifier) {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text("Género") },
            trailingIcon = {
                IconButton(onClick = { abierto = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Elegir género")
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        DropdownMenu(expanded = abierto, onDismissRequest = { abierto = false }) {
            listOf("Femenino", "Masculino", "Otro", "Prefiero no decir").forEach { opcion ->
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
private fun RegistroPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { RegistroScreen(onAtras = {}, onCrearCuenta = {}) }
    }
}