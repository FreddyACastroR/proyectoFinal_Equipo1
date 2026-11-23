package mx.itson.hogarea.datos

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

const val CONTRASENA_MUESTRA = "hogarea123"

object Sesion {
    var usuario by mutableStateOf(Persona.ANGEL)
    var hogarActivo by mutableStateOf(DatosMuestra.casaNainari)

    fun iniciar(correo: String, contrasena: String): Boolean {
        val persona = Persona.entries.firstOrNull { it.correo.equals(correo.trim(), ignoreCase = true) }
        if (persona == null || contrasena != CONTRASENA_MUESTRA) return false
        usuario = persona
        return true
    }
}

fun correoValido(texto: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(texto.trim()).matches()

fun contrasenaValida(texto: String): Boolean = texto.length >= 8 && texto.any { it.isDigit() }

fun esAdmin(hogar: Hogar): Boolean = hogar.creador == Sesion.usuario

fun puedeEditarTareas(hogar: Hogar): Boolean = esAdmin(hogar) || hogar.miembrosPuedenEditar