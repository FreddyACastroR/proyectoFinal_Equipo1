package mx.itson.hogarea.datos

import androidx.compose.runtime.mutableStateListOf

// Datos de muestra tomados del Figma. Todas las pantallas leen de aquí.

enum class Persona(val nombre: String, val inicial: String, val correo: String) {
    FREDDY("Freddy Castro", "F", "freddy@correo.com"),
    RUBEN("Rubén", "R", "ruben@correo.com"),
    ANGEL("Ángel", "Á", "angel@correo.com"),
    ANDRE("André", "A", "andre@correo.com"),
}

data class Hogar(
    val nombre: String,
    val creador: Persona,
    val miembros: List<Persona>,
    val codigo: String,
    val miembrosPuedenEditar: Boolean,
)

data class Habitacion(val nombre: String, val predeterminadas: List<String>)

enum class Estado { PENDIENTE, COMPLETADA, PAUSADA }

data class Tarea(
    val id: Int,
    val nombre: String,
    val subtitulo: String,
    val dia: String,
    val hogar: String,
    val habitacion: String,
    val asignados: List<Persona>,
    val estado: Estado = Estado.PENDIENTE,
    val descripcion: String = "",
    val recurrencia: String = "Semanal",
    val reparto: String = "Solo una persona",
    val tipo: String = "Manual",
)

data class Notificacion(val titulo: String, val detalle: String, val hora: String, val cuando: String)

data class Completada(
    val tarea: String,
    val detalle: String,
    val quienes: List<Persona>,
    val puntos: Int,
    val cuando: String,
)

object DatosMuestra {
    val casaNainari = Hogar(
        nombre = "Casa Nainari",
        creador = Persona.ANGEL,
        miembros = listOf(Persona.ANGEL, Persona.FREDDY, Persona.RUBEN, Persona.ANDRE),
        codigo = "CASA-7Q29",
        miembrosPuedenEditar = true,
    )
    val depaObregon = Hogar(
        nombre = "Depa Obregón",
        creador = Persona.ANGEL,
        miembros = listOf(Persona.ANGEL, Persona.RUBEN, Persona.FREDDY, Persona.ANDRE),
        codigo = "DEPA-K3M8",
        miembrosPuedenEditar = false,
    )
    val hogares = listOf(casaNainari, depaObregon)

    val habitaciones = listOf(
        Habitacion("Cocina", listOf("Lavar los trastes", "Limpiar la estufa", "Trapear la cocina")),
        Habitacion("Sala", listOf("Barrer la sala", "Sacudir muebles")),
        Habitacion("Baño", listOf("Limpiar el baño", "Cambiar toallas")),
        Habitacion("Recámara principal", listOf("Tender la cama", "Cambiar sábanas")),
        Habitacion("Patio", listOf("Sacar la basura", "Tender la ropa")),
    )

    val tareas = mutableStateListOf(
        Tarea(
            1, "Barrer la sala", "Sala · Casa Nainari · tu turno", "Hoy · jueves 15",
            "Casa Nainari", "Sala", listOf(Persona.RUBEN, Persona.FREDDY),
            descripcion = "Barrer debajo de los sillones y la mesa de centro, y sacudir el tapete en el patio.",
            reparto = "Dividir días · al azar", tipo = "Predeterminada de Sala",
        ),
        Tarea(
            2, "Limpiar el baño", "Baño · Depa Obregón", "Hoy · jueves 15",
            "Depa Obregón", "Baño", listOf(Persona.RUBEN),
            descripcion = "Tallar regadera, lavabo y taza, y cambiar la toalla de manos.",
        ),
        Tarea(
            3, "Limpiar ventanas", "Pausada hasta el jue 22 oct", "Hoy · jueves 15",
            "Casa Nainari", "Toda la casa", listOf(Persona.RUBEN), Estado.PAUSADA,
        ),
        Tarea(
            4, "Lavar los trastes", "Cocina · Casa Nainari · en equipo", "Hoy · jueves 15",
            "Casa Nainari", "Cocina", listOf(Persona.RUBEN, Persona.ANGEL), Estado.COMPLETADA,
            reparto = "En equipo",
        ),
        Tarea(
            5, "Sacar la basura", "Patio · Casa Nainari", "Hoy · jueves 15",
            "Casa Nainari", "Patio", listOf(Persona.RUBEN), Estado.COMPLETADA,
        ),
        Tarea(
            6, "Tender la ropa", "Patio · Casa Nainari", "Hoy · jueves 15",
            "Casa Nainari", "Patio", listOf(Persona.ANDRE, Persona.RUBEN), Estado.COMPLETADA,
        ),
        Tarea(
            7, "Trapear la cocina", "Cocina · Casa Nainari", "Viernes 16",
            "Casa Nainari", "Cocina", listOf(Persona.RUBEN),
        ),
        Tarea(
            8, "Comprar despensa", "Toda la casa · Depa Obregón", "Viernes 16",
            "Depa Obregón", "Toda la casa", listOf(Persona.RUBEN, Persona.ANGEL, Persona.ANDRE),
            reparto = "En equipo",
        ),
        Tarea(
            9, "Lavar los trastes", "Cocina · asignada a Ángel", "Hoy · jueves 15",
            "Depa Obregón", "Cocina", listOf(Persona.ANGEL),
        ),
        Tarea(
            10, "Regar las plantas", "Toda la casa · André", "Hoy · jueves 15",
            "Depa Obregón", "Toda la casa", listOf(Persona.ANDRE), Estado.COMPLETADA,
        ),
        Tarea(
            11, "Sacar la basura", "Patio · asignada a André", "Viernes 16",
            "Depa Obregón", "Patio", listOf(Persona.ANDRE),
        ),
    )

    fun tarea(id: Int): Tarea = tareas.first { it.id == id }

    fun cambiarEstado(id: Int, estado: Estado) {
        val i = tareas.indexOfFirst { it.id == id }
        if (i >= 0) tareas[i] = tareas[i].copy(estado = estado)
    }

    val completadasDelMes = listOf(
        Persona.ANDRE to 14, Persona.RUBEN to 12, Persona.FREDDY to 10, Persona.ANGEL to 6,
    )
    val puntos = listOf(
        Persona.ANDRE to 120, Persona.RUBEN to 105, Persona.FREDDY to 90, Persona.ANGEL to 55,
    )
    val recompensas = listOf("Elegir la película" to 150, "Un día sin lavar trastes" to 200)

    val notificaciones = listOf(
        Notificacion("Te toca: Barrer la sala", "Casa Nainari · hoy jueves", "9:00", "Hoy"),
        Notificacion("Recordatorio: Limpiar el baño", "Depa Obregón · vence hoy", "8:00", "Hoy"),
        Notificacion("Ángel completó Lavar los trastes", "Casa Nainari", "9:20", "Hoy"),
        Notificacion("Ángel pausó Limpiar el refri", "Depa Obregón · hasta el jue 22 oct", "6:30 p. m.", "Ayer"),
        Notificacion("Ganaste 10 puntos", "Por completar Sacar la basura a tiempo", "8:10 a. m.", "Ayer"),
        Notificacion("Freddy se unió a Casa Nainari", "Ya puedes asignarle tareas", "7:00 a. m.", "Ayer"),
    )

    val historial = listOf(
        Completada("Lavar los trastes", "Cocina · Casa Nainari · 9:20", listOf(Persona.RUBEN, Persona.ANGEL), 10, "Hoy · jueves 15"),
        Completada("Tender la ropa", "Patio · Casa Nainari · 8:40", listOf(Persona.ANDRE, Persona.RUBEN), 10, "Hoy · jueves 15"),
        Completada("Sacar la basura", "Patio · Casa Nainari · 8:05", listOf(Persona.RUBEN), 10, "Hoy · jueves 15"),
        Completada("Regar las plantas", "Depa Obregón · 19:30", listOf(Persona.ANGEL), 10, "Ayer · miércoles 14"),
        Completada("Sacudir muebles", "Sala · Casa Nainari · 18:10", listOf(Persona.FREDDY), 5, "Ayer · miércoles 14"),
        Completada("Limpiar la estufa", "Cocina · Casa Nainari · 14:15", listOf(Persona.ANGEL), 10, "Ayer · miércoles 14"),
    )
}