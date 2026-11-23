package mx.itson.hogarea.datos

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import java.time.LocalDate

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
    val icono: Int = 0,                           // posición en iconosHogar
    val color: Int = 0,                           // posición en coloresEtiqueta
    val roles: Map<Persona, String> = emptyMap(), // rol familiar de cada miembro
)

data class Habitacion(
    val nombre: String,
    val predeterminadas: List<String>,
    val icono: Int = 14,                          // posición en iconosHabitacion
    val color: Int = 0,                           // posición en coloresEtiqueta
)

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
    val dias: Set<Int> = emptySet(),              // 0 = lunes … 6 = domingo; vacío en tareas manuales
    val fecha: LocalDate? = null,                 // solo en tareas manuales
    val pausadaHasta: LocalDate? = null,
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
        creador = Persona.RUBEN,
        miembros = listOf(Persona.RUBEN, Persona.ANGEL, Persona.FREDDY, Persona.ANDRE),
        codigo = "DEPA-K3M8",
        miembrosPuedenEditar = false,
        icono = 2,
        color = 2,
    )
    val hogares = mutableStateListOf(casaNainari, depaObregon)

    val habitaciones = mutableStateListOf(
        Habitacion("Cocina", listOf("Lavar los trastes", "Limpiar la estufa", "Trapear la cocina"), icono = 0, color = 2),
        Habitacion("Sala", listOf("Barrer la sala", "Sacudir muebles"), icono = 1, color = 0),
        Habitacion("Baño", listOf("Limpiar el baño", "Cambiar toallas"), icono = 2, color = 1),
        Habitacion("Recámara principal", listOf("Tender la cama", "Cambiar sábanas"), icono = 3, color = 7),
        Habitacion("Patio", listOf("Sacar la basura", "Tender la ropa"), icono = 4, color = 3),
    )

    val tareas = mutableStateListOf(
        Tarea(
            1, "Barrer la sala", "Sala · Casa Nainari · tu turno", "Hoy · jueves 15",
            "Casa Nainari", "Sala", listOf(Persona.ANGEL, Persona.FREDDY),
            descripcion = "Barrer debajo de los sillones y la mesa de centro, y sacudir el tapete en el patio.",
            reparto = "Dividir días · al azar", tipo = "Predeterminada de Sala", dias = setOf(3),
        ),
        Tarea(
            2, "Limpiar el baño", "Baño · Depa Obregón", "Hoy · jueves 15",
            "Depa Obregón", "Baño", listOf(Persona.ANGEL),
            descripcion = "Tallar regadera, lavabo y taza, y cambiar la toalla de manos.",
        ),
        Tarea(
            3, "Limpiar ventanas", "Pausada hasta el jue 22 oct", "Hoy · jueves 15",
            "Casa Nainari", "Toda la casa", listOf(Persona.ANGEL), Estado.PAUSADA,
            descripcion = "Por dentro y por fuera, con limpiavidrios y papel periódico.",
        ),
        Tarea(
            4, "Lavar los trastes", "Cocina · Casa Nainari · en equipo", "Hoy · jueves 15",
            "Casa Nainari", "Cocina", listOf(Persona.ANGEL, Persona.RUBEN), Estado.COMPLETADA,
            descripcion = "Lavar, secar y guardar. La estufa queda limpia al terminar.",
            reparto = "En equipo",
        ),
        Tarea(
            5, "Sacar la basura", "Patio · Casa Nainari", "Hoy · jueves 15",
            "Casa Nainari", "Patio", listOf(Persona.ANGEL), Estado.COMPLETADA,
            descripcion = "Juntar los botes de cocina y baños, y sacarlos antes de las 8.",
        ),
        Tarea(
            6, "Tender la ropa", "Patio · Casa Nainari", "Hoy · jueves 15",
            "Casa Nainari", "Patio", listOf(Persona.ANDRE, Persona.ANGEL), Estado.COMPLETADA,
            descripcion = "Tender lo de la lavadora y recoger lo que ya esté seco.",
        ),
        Tarea(
            7, "Trapear la cocina", "Cocina · Casa Nainari", "Viernes 16",
            "Casa Nainari", "Cocina", listOf(Persona.ANGEL),
            descripcion = "Barrer primero y trapear con desengrasante.",
        ),
        Tarea(
            8, "Comprar despensa", "Toda la casa · Depa Obregón", "Viernes 16",
            "Depa Obregón", "Toda la casa", listOf(Persona.ANGEL, Persona.RUBEN, Persona.ANDRE),
            descripcion = "Con la lista del refrigerador. Se divide el total entre los tres.",
            reparto = "En equipo",
        ),
        Tarea(
            9, "Lavar los trastes", "Cocina · asignada a Rubén", "Hoy · jueves 15",
            "Depa Obregón", "Cocina", listOf(Persona.RUBEN),
            descripcion = "Los de la comida y la cena.",
        ),
        Tarea(
            10, "Regar las plantas", "Toda la casa · André", "Hoy · jueves 15",
            "Depa Obregón", "Toda la casa", listOf(Persona.ANDRE), Estado.COMPLETADA,
            descripcion = "Las del balcón cada tercer día; las de adentro, una vez por semana.",
        ),
        Tarea(
            11, "Sacar la basura", "Patio · asignada a André", "Viernes 16",
            "Depa Obregón", "Patio", listOf(Persona.ANDRE),
            descripcion = "El camión pasa lunes, miércoles y viernes.",
        ),
    )

    fun tarea(id: Int): Tarea = tareas.first { it.id == id }

    fun hogar(nombre: String): Hogar = hogares.first { it.nombre == nombre }

    fun cambiarEstado(id: Int, estado: Estado) {
        val i = tareas.indexOfFirst { it.id == id }
        if (i < 0) return
        val antes = tareas[i]
        tareas[i] = antes.copy(estado = estado)
        if (estado == Estado.COMPLETADA && antes.estado != Estado.COMPLETADA) {
            antes.asignados.forEach { sumarPuntos(it, 10) }
        }
    }

    fun guardarTarea(tarea: Tarea) {
        val i = tareas.indexOfFirst { it.id == tarea.id }
        if (i >= 0) tareas[i] = tarea else tareas.add(tarea)
    }

    fun nuevoIdTarea(): Int = (tareas.maxOfOrNull { it.id } ?: 0) + 1

    fun guardarHabitacion(indice: Int?, habitacion: Habitacion) {
        if (indice == null) habitaciones.add(habitacion) else habitaciones[indice] = habitacion
    }

    fun rolDe(hogar: Hogar, persona: Persona): String = hogar.roles[persona] ?: "Roomie"

    fun guardarHogar(anterior: Hogar?, hogar: Hogar) {
        val i = hogares.indexOfFirst { it.nombre == anterior?.nombre }
        if (i >= 0) hogares[i] = hogar else hogares.add(hogar)
        if (anterior == null || Sesion.hogarActivo.nombre == anterior.nombre) Sesion.hogarActivo = hogar
        // Si cambió el nombre, las tareas de ese hogar lo siguen
        if (anterior != null && anterior.nombre != hogar.nombre) {
            for (j in tareas.indices) {
                if (tareas[j].hogar == anterior.nombre) tareas[j] = tareas[j].copy(hogar = hogar.nombre)
            }
        }
    }

    fun quitarDelHogar(hogar: Hogar, persona: Persona) {
        guardarHogar(hogar, hogar.copy(miembros = hogar.miembros - persona))
        if (persona == Sesion.usuario) {
            hogares.firstOrNull { persona in it.miembros }?.let { Sesion.hogarActivo = it }
        }
    }

    fun cambiarRol(hogar: Hogar, persona: Persona, rol: String) {
        guardarHogar(hogar, hogar.copy(roles = hogar.roles + (persona to rol)))
    }

    val completadasDelMes = listOf(
        Persona.ANDRE to 14, Persona.RUBEN to 12, Persona.FREDDY to 10, Persona.ANGEL to 6,
    )

    val puntos = mutableStateListOf(
        Persona.ANDRE to 120, Persona.RUBEN to 105, Persona.FREDDY to 90, Persona.ANGEL to 55,
    )

    val disponibles = mutableStateMapOf(
        Persona.ANDRE to 120, Persona.RUBEN to 105, Persona.FREDDY to 90, Persona.ANGEL to 55,
    )
    val recompensas = mutableStateListOf("Elegir la película" to 150, "Un día sin lavar trastes" to 200)

    val canjes = mutableStateListOf<Triple<Persona, String, Int>>()

    val reconocimientos = listOf(
        Triple("Más puntual", Persona.RUBEN, "11 de 12 tareas a tiempo"),
        Triple("Racha más larga", Persona.FREDDY, "6 días seguidos"),
    )

    fun sumarPuntos(persona: Persona, cantidad: Int) {
        val i = puntos.indexOfFirst { it.first == persona }
        if (i >= 0) puntos[i] = persona to puntos[i].second + cantidad
        val ordenados = puntos.sortedByDescending { it.second }
        puntos.clear()
        puntos.addAll(ordenados)
        disponibles[persona] = (disponibles[persona] ?: 0) + cantidad
    }

    fun canjear(persona: Persona, recompensa: Pair<String, Int>): Boolean {
        val saldo = disponibles[persona] ?: 0
        if (saldo < recompensa.second) return false
        disponibles[persona] = saldo - recompensa.second
        canjes.add(0, Triple(persona, recompensa.first, recompensa.second))
        return true
    }

    val notificaciones = listOf(
        Notificacion("Te toca: Barrer la sala", "Casa Nainari · hoy jueves", "9:00", "Hoy"),
        Notificacion("Recordatorio: Limpiar el baño", "Depa Obregón · vence hoy", "8:00", "Hoy"),
        Notificacion("Rubén completó Lavar los trastes", "Casa Nainari", "9:20", "Hoy"),
        Notificacion("Rubén pausó Limpiar el refri", "Depa Obregón · hasta el jue 22 oct", "6:30 p. m.", "Ayer"),
        Notificacion("Ganaste 10 puntos", "Por completar Sacar la basura a tiempo", "8:10 a. m.", "Ayer"),
        Notificacion("Freddy se unió a Casa Nainari", "Ya puedes asignarle tareas", "7:00 a. m.", "Ayer"),
    )

    val historial = listOf(
        Completada("Lavar los trastes", "Cocina · Casa Nainari · 9:20", listOf(Persona.ANGEL, Persona.RUBEN), 10, "Hoy · jueves 15"),
        Completada("Tender la ropa", "Patio · Casa Nainari · 8:40", listOf(Persona.ANDRE, Persona.ANGEL), 10, "Hoy · jueves 15"),
        Completada("Sacar la basura", "Patio · Casa Nainari · 8:05", listOf(Persona.ANGEL), 10, "Hoy · jueves 15"),
        Completada("Regar las plantas", "Depa Obregón · 19:30", listOf(Persona.RUBEN), 10, "Ayer · miércoles 14"),
        Completada("Sacudir muebles", "Sala · Casa Nainari · 18:10", listOf(Persona.FREDDY), 5, "Ayer · miércoles 14"),
        Completada("Limpiar la estufa", "Cocina · Casa Nainari · 14:15", listOf(Persona.RUBEN), 10, "Ayer · miércoles 14"),
    )
}