package mx.itson.hogarea.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// El fondo de todas las pantallas
@Composable
fun FondoHogarea(content: @Composable () -> Unit) {
    val oscuro = LocalTema.current.oscuro
    val indigo = if (oscuro) Color(0xFF262A6B) else Color(0xFFC9CCFB)
    val brillo = if (oscuro) Color(0xB34A2213) else Color(0xBFFFCFBC)
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .background(Brush.verticalGradient(0f to indigo, 0.5f to indigo.copy(alpha = 0f)))
            .drawBehind {
                val radio = size.width * 0.61f
                val centro = Offset(size.width, 0f)
                drawCircle(
                    brush = Brush.radialGradient(listOf(brillo, brillo.copy(alpha = 0f)), centro, radio),
                    radius = radio,
                    center = centro,
                )
            }
    ) { content() }
}