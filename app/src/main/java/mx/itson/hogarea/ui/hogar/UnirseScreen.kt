package mx.itson.hogarea.ui.hogar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.componentes.BarraSuperior
import mx.itson.hogarea.ui.theme.Exito
import mx.itson.hogarea.ui.theme.ExitoOscuro
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme
import mx.itson.hogarea.ui.theme.LocalTema

// 04d y 04e · Unirse con código. DEPA-K3M8 encuentra Depa Obregón; otro código completo es 04e (error).
@Composable
fun UnirseScreen(onAtras: () -> Unit, onUnirme: () -> Unit) {
    val cs = MaterialTheme.colorScheme
    val hogar = DatosMuestra.depaObregon
    var codigo by rememberSaveable { mutableStateOf(hogar.codigo) }
    val valido = codigo.trim() == hogar.codigo
    // 04e: un código completo que no es el de Depa Obregón
    val error = codigo.length >= hogar.codigo.length && !valido

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BarraSuperior("Unirme a un hogar", onAtras = onAtras) },
        bottomBar = {
            Button(
                onClick = onUnirme,
                enabled = valido,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(48.dp),
            ) { Text("Unirme al hogar") }
        },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "Escribe el código de invitación que te compartió el creador del hogar.",
                style = MaterialTheme.typography.bodyLarge,
                color = cs.onSurfaceVariant,
            )
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it.uppercase() },
                label = { Text("Código de invitación") },
                leadingIcon = { Icon(Icons.Filled.Key, contentDescription = null) },
                isError = error,
                supportingText = {
                    Text(
                        if (error) "Ese código no existe o ya venció. Pídele uno nuevo al creador del hogar."
                        else "Ejemplo: CASA-7Q29",
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            if (valido) {
                Text(
                    "Hogar encontrado",
                    style = MaterialTheme.typography.titleSmall,
                    color = if (LocalTema.current.oscuro) ExitoOscuro else Exito,
                )
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = cs.surfaceContainerLowest)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(40.dp).clip(CircleShape).background(cs.tertiaryContainer),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(Icons.Filled.Home, contentDescription = null, tint = cs.onTertiaryContainer)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(hogar.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "Creado por ${hogar.creador.nombre} · 3 miembros",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = cs.onSurfaceVariant,
                                )
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf(Persona.ANGEL, Persona.RUBEN, Persona.ANDRE).forEach { Avatar(it) }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(18.dp), tint = cs.onSurfaceVariant)
                            Text(
                                "En este hogar podrás ver tus tareas y completarlas, pero no editarlas.",
                                style = MaterialTheme.typography.bodySmall,
                                color = cs.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun UnirsePreview() {
    HogareaTheme(oscuro = false, onCambiarTema = {}) {
        FondoHogarea { UnirseScreen(onAtras = {}, onUnirme = {}) }
    }
}