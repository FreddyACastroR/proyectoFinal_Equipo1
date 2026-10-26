package mx.itson.hogarea.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

// El interruptor tema oscuro
class TemaApp(val oscuro: Boolean, val cambiar: (Boolean) -> Unit)

val LocalTema = compositionLocalOf { TemaApp(oscuro = false, cambiar = {}) }

@Composable
fun HogareaTheme(
    oscuro: Boolean,
    onCambiarTema: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalTema provides TemaApp(oscuro, onCambiarTema)) {
        MaterialTheme(
            colorScheme = if (oscuro) DarkColors else LightColors,
            typography = Typography,
            content = content,
        )
    }
}