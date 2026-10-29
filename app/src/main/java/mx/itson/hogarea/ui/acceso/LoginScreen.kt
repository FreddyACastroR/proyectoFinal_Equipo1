package mx.itson.hogarea.ui.acceso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

@Composable
fun LoginScreen(onEntrar: () -> Unit, onOlvide: () -> Unit, onRegistro: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    var correo by rememberSaveable { mutableStateOf("angel@correo.com") }
    var contrasena by rememberSaveable { mutableStateOf("hogarea123") }
    var verContrasena by rememberSaveable { mutableStateOf(false) }

    Scaffold(containerColor = Color.Transparent) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(Modifier.height(32.dp))
            Box(
                Modifier.size(72.dp).clip(RoundedCornerShape(20.dp)).background(cs.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Filled.Home, contentDescription = null, tint = cs.onPrimaryContainer, modifier = Modifier.size(40.dp))
            }
            Text("Hogarea", style = MaterialTheme.typography.displaySmall, color = cs.primary)
            Text(
                "Las tareas de la casa, repartidas entre todos",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
            )
            Text(
                "Inicia sesión",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            )
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
                trailingIcon = {
                    IconButton(onClick = { verContrasena = !verContrasena }) {
                        Icon(
                            if (verContrasena) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (verContrasena) "Ocultar contraseña" else "Mostrar contraseña",
                        )
                    }
                },
                visualTransformation = if (verContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            TextButton(onClick = onOlvide, modifier = Modifier.align(Alignment.End)) {
                Text("¿Olvidaste tu contraseña?")
            }
            Button(
                onClick = onEntrar,
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) { Text("Iniciar sesión") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(Modifier.weight(1f))
                Text("o", modifier = Modifier.padding(horizontal = 16.dp), color = cs.onSurfaceVariant)
                HorizontalDivider(Modifier.weight(1f))
            }
            OutlinedButton(
                onClick = { }, // la hoja de huella llega en 01b
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Icon(Icons.Filled.Fingerprint, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Entrar con huella digital")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes cuenta?", color = cs.onSurfaceVariant)
                TextButton(onClick = onRegistro) { Text("Regístrate") }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun LoginPreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { LoginScreen(onEntrar = {}, onOlvide = {}, onRegistro = {}) }
    }
}