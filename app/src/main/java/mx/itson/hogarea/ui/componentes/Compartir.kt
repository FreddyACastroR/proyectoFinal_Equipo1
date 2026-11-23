package mx.itson.hogarea.ui.componentes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

fun copiarCodigo(contexto: Context, codigo: String) {
    val portapapeles = contexto.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    portapapeles.setPrimaryClip(ClipData.newPlainText("Código de invitación", codigo))
}

fun compartirCodigo(contexto: Context, hogar: String, codigo: String) {
    val envio = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Únete a $hogar en Hogarea con el código $codigo")
    }
    contexto.startActivity(Intent.createChooser(envio, "Compartir código"))
}