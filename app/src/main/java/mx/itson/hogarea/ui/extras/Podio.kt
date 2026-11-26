package mx.itson.hogarea.ui.extras

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.itson.hogarea.datos.Persona
import mx.itson.hogarea.ui.componentes.Avatar
import mx.itson.hogarea.ui.theme.Bronce
import mx.itson.hogarea.ui.theme.BronceOscuro
import mx.itson.hogarea.ui.theme.LocalTema
import mx.itson.hogarea.ui.theme.Oro
import mx.itson.hogarea.ui.theme.OroOscuro
import mx.itson.hogarea.ui.theme.Plata
import mx.itson.hogarea.ui.theme.PlataOscuro

// Los tres primeros lugares con su trofeo. El primero va al centro y más alto.
@Composable
fun Podio(lugares: List<Pair<Persona, Int>>, modifier: Modifier = Modifier) {
    val oscuro = LocalTema.current.oscuro
    val trofeos = listOf(
        if (oscuro) OroOscuro else Oro,
        if (oscuro) PlataOscuro else Plata,
        if (oscuro) BronceOscuro else Bronce,
    )
    // Orden en pantalla: segundo, primero, tercero
    val orden = listOf(1, 0, 2).filter { it < lugares.size }
    Card(
        modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
        ) {
            orden.forEach { lugar ->
                val (persona, puntos) = lugares[lugar]
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        Icons.Filled.EmojiEvents,
                        contentDescription = "Lugar ${lugar + 1}",
                        tint = trofeos[lugar],
                        modifier = Modifier.size(if (lugar == 0) 48.dp else 36.dp),
                    )
                    Avatar(persona, tamano = if (lugar == 0) 56.dp else 44.dp)
                    Text(persona.nombre.substringBefore(" "), style = MaterialTheme.typography.titleSmall)
                    Text(
                        "$puntos pts",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}