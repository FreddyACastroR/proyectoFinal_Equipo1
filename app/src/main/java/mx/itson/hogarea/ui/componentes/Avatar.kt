package mx.itson.hogarea.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.theme.EnExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedor
import mx.itson.hogarea.ui.theme.ExitoContenedorOscuro

@Composable
fun Avatar(persona: Persona, modifier: Modifier = Modifier, tamano: Dp = 32.dp) {
    val cs = MaterialTheme.colorScheme
    val oscuro = cs.surface.luminance() < 0.5f
    val (fondo, texto) = when (persona) {
        Persona.FREDDY -> cs.primaryContainer to cs.onPrimaryContainer
        Persona.RUBEN -> cs.tertiaryContainer to cs.onTertiaryContainer
        Persona.ANGEL -> cs.secondaryContainer to cs.onSecondaryContainer
        Persona.ANDRE -> if (oscuro) ExitoContenedorOscuro to ExitoContenedor else ExitoContenedor to EnExitoContenedor
    }
    Box(
        modifier = modifier.size(tamano).clip(CircleShape).background(fondo),
        contentAlignment = Alignment.Center,
    ) {
        Text(persona.inicial, color = texto, fontSize = (tamano.value * 0.42f).sp)
    }
}