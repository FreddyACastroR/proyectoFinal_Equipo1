package mx.itson.hogarea.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Colores del Figma
val LightColors = lightColorScheme(
    primary = Color(0xFF4C51C6), onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE0E0FF), onPrimaryContainer = Color(0xFF0C0E62),
    secondary = Color(0xFF3D5F90), onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD5E3FF), onSecondaryContainer = Color(0xFF001B3C),
    tertiary = Color(0xFF9C4323), onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDBCF), onTertiaryContainer = Color(0xFF3A0B00),
    error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF410002),
    background = Color(0xFFEFF0FB), onBackground = Color(0xFF1B1B22),
    surface = Color(0xFFEFF0FB), onSurface = Color(0xFF1B1B22),
    onSurfaceVariant = Color(0xFF46464F),
    outline = Color(0xFF777680), outlineVariant = Color(0xFFC7C6D6),
    inverseSurface = Color(0xFF303036), inverseOnSurface = Color(0xFFF3EFF7),
    surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFFBFBFF),
    surfaceContainer = Color(0xFFF5F5FD), surfaceContainerHigh = Color(0xFFE5E5F3),
    surfaceContainerHighest = Color(0xFFDBDCEC),
)

val DarkColors = darkColorScheme(
    primary = Color(0xFFBEC2FF), onPrimary = Color(0xFF1A2391),
    primaryContainer = Color(0xFF343BAE), onPrimaryContainer = Color(0xFFE0E0FF),
    secondary = Color(0xFFA6C8FF), onSecondary = Color(0xFF003060),
    secondaryContainer = Color(0xFF234776), onSecondaryContainer = Color(0xFFD5E3FF),
    tertiary = Color(0xFFFFB59C), onTertiary = Color(0xFF5C1A00),
    tertiaryContainer = Color(0xFF7E2C0E), onTertiaryContainer = Color(0xFFFFDBCF),
    error = Color(0xFFFFB4AB), onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF12131C), onBackground = Color(0xFFE4E1EC),
    surface = Color(0xFF12131C), onSurface = Color(0xFFE4E1EC),
    onSurfaceVariant = Color(0xFFC7C5D0),
    outline = Color(0xFF918F9A), outlineVariant = Color(0xFF46464F),
    inverseSurface = Color(0xFFE4E1EC), inverseOnSurface = Color(0xFF303036),
    surfaceContainerLowest = Color(0xFF0D0E16), surfaceContainerLow = Color(0xFF1B1C26),
    surfaceContainer = Color(0xFF1F202A), surfaceContainerHigh = Color(0xFF2A2B35),
    surfaceContainerHighest = Color(0xFF353640),
)

val Exito = Color(0xFF2E7D32)
val ExitoOscuro = Color(0xFF81C784)
val ExitoContenedor = Color(0xFFC8E6C9)
val ExitoContenedorOscuro = Color(0xFF1B5E20)
val EnExitoContenedor = Color(0xFF0B3D10)

val FondoIndigo = Color(0xFFC9CCFB)
val FondoIndigoOscuro = Color(0xFF262A6B)
val FondoBrillo = Color(0xBFFFCFBC)
val FondoBrilloOscuro = Color(0xB34A2213)

val Oro = Color(0xFF8A6500)
val OroOscuro = Color(0xFFF2C94C)
val Plata = Color(0xFF5F6670)
val PlataOscuro = Color(0xFFC7CDD6)
val Bronce = Color(0xFF8A4B20)
val BronceOscuro = Color(0xFFE0A070)

class ColorEtiqueta(
    val nombre: String,
    val claro: Color,
    val enClaro: Color,
    val oscuro: Color,
    val enOscuro: Color,
)

val coloresEtiqueta = listOf(
    ColorEtiqueta("Índigo", Color(0xFFE0E0FF), Color(0xFF0C0E62), Color(0xFF343BAE), Color(0xFFE0E0FF)),
    ColorEtiqueta("Azul", Color(0xFFD5E3FF), Color(0xFF001B3C), Color(0xFF234776), Color(0xFFD5E3FF)),
    ColorEtiqueta("Coral", Color(0xFFFFDBCF), Color(0xFF3A0B00), Color(0xFF7E2C0E), Color(0xFFFFDBCF)),
    ColorEtiqueta("Verde", Color(0xFFC8E6C9), Color(0xFF0B3D10), Color(0xFF1B5E20), Color(0xFFC8E6C9)),
    ColorEtiqueta("Amarillo", Color(0xFFFFE9A8), Color(0xFF2A1F00), Color(0xFF5C4500), Color(0xFFFFE9A8)),
    ColorEtiqueta("Rosa", Color(0xFFFFD9E4), Color(0xFF3E0020), Color(0xFF7A2947), Color(0xFFFFD9E4)),
    ColorEtiqueta("Turquesa", Color(0xFFC4EEF0), Color(0xFF002022), Color(0xFF0E5257), Color(0xFFC4EEF0)),
    ColorEtiqueta("Morado", Color(0xFFEBDDFF), Color(0xFF22005D), Color(0xFF553A8C), Color(0xFFEBDDFF)),
)