package mx.itson.hogarea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import mx.itson.hogarea.ui.navegacion.NavGraph
import mx.itson.hogarea.ui.theme.FondoHogarea
import mx.itson.hogarea.ui.theme.HogareaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var preferencia by rememberSaveable { mutableStateOf<Boolean?>(null) }
            val oscuro = preferencia ?: isSystemInDarkTheme()
            HogareaTheme(oscuro = oscuro, onCambiarTema = { preferencia = it }) {
                FondoHogarea {
                    NavGraph()
                }
            }
        }
    }
}