package mx.itson.hogarea.ui.revision

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val RUTA_REVISION = "revision"

// Solo en la rama demo/revision. Abre cada ruta del camino en orden,
// así la pila queda igual que en la app real.
fun NavGraphBuilder.revision(nav: NavController) {
    composable(RUTA_REVISION) {
        MenuRevisionScreen(onAbrir = { camino -> camino.forEach { nav.navigate(it) } })
    }
}
