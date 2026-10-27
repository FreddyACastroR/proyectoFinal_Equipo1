package mx.itson.hogarea.ui.detalle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import mx.itson.hogarea.datos.DatosMuestra
import mx.itson.hogarea.datos.Estado
import mx.itson.hogarea.ui.componentes.HojaHuella

// 07c · Junta el detalle con sus dos hojas (pausar y eliminar con huella)
// y cambia la tarea en los datos de muestra. La lista de 05 se actualiza sola.
@Composable
fun DetalleTarea(id: Int, puedeEditar: Boolean, onAtras: () -> Unit, onEditar: () -> Unit) {
    var hoja by remember { mutableStateOf<String?>(null) } // "pausar", "eliminar" o null

    DetalleTareaScreen(
        id = id,
        puedeEditar = puedeEditar,
        onAtras = onAtras,
        onEditar = onEditar,
        onPausar = { hoja = "pausar" },
        onEliminar = { hoja = "eliminar" },
        onCompletar = {
            DatosMuestra.cambiarEstado(id, Estado.COMPLETADA)
            onAtras()
        },
    )

    val tarea = DatosMuestra.tareas.firstOrNull { it.id == id } ?: return
    when (hoja) {
        "pausar" -> HojaPausar(
            tarea = tarea,
            onCancelar = { hoja = null },
            onPausar = {
                DatosMuestra.cambiarEstado(id, Estado.PAUSADA)
                hoja = null
                onAtras()
            },
        )
        "eliminar" -> HojaHuella(
            titulo = "Confirma para eliminar",
            texto = "“${tarea.nombre}” se eliminará para todos los miembros del hogar. Usa tu huella para confirmar.",
            onCancelar = { hoja = null },
            onSensor = {
                hoja = null
                onAtras()
                DatosMuestra.tareas.removeAll { it.id == id }
            },
        )
    }
}