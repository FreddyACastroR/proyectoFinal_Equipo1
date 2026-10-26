package mx.itson.hogarea.ui.navegacion

// Todas las rutas de la app.
object Rutas {
    // Acceso
    const val LOGIN = "login"                              // 01, 01b y 01c
    const val RECUPERAR = "recuperar"                      // 02
    const val REGISTRO = "registro"                        // 03

    // Crear o unirse a un hogar
    const val BIENVENIDA = "hogar/bienvenida"              // 04
    const val CREAR_HOGAR = "hogar/crear"                  // 04b
    const val HOGAR_CREADO = "hogar/creado"                // 04c
    const val UNIRSE = "hogar/unirse"                      // 04d y 04e

    // Mis tareas
    const val MIS_TAREAS = "tareas"                        // 05
    const val TAREAS_HOGAR = "tareas/hogar"                // 05b

    // Crear y editar tareas
    const val NUEVA_TAREA = "tarea/nueva"                  // 06a y 06e
    const val ASIGNAR_TAREA = "tarea/asignar"              // 06b
    const val EDITAR_TAREA = "tarea/editar"                // 06c
    const val EDITAR_ASIGNACION = "tarea/editar/asignar"   // 06d

    // Detalle de tarea
    const val DETALLE = "tarea/detalle/{id}"               // 07, 07b y 07c
    const val DETALLE_LECTURA = "tarea/lectura/{id}"       // 07d
    fun detalle(id: Int) = "tarea/detalle/$id"
    fun detalleLectura(id: Int) = "tarea/lectura/$id"

    // Configurar el hogar
    const val CONFIG_HOGAR = "config"                      // 08 y 08h
    const val HABITACIONES = "config/habitaciones"         // 08b y 08c
    const val MIEMBROS = "config/miembros"                 // 08d
    const val MIEMBRO = "config/miembro"                   // 08e
    const val EDITAR_HABITACION = "config/habitacion/editar"   // 08f
    const val AGREGAR_HABITACION = "config/habitacion/agregar" // 08g
    const val HOGAR_MIEMBRO = "config/vista-miembro"       // 08i

    // Estadísticas
    const val ESTADISTICAS = "estadisticas"                // 09 y 09b

    // Perfil y modo oscuro
    const val PERFIL = "perfil"                            // 10
    const val EDITAR_PERFIL = "perfil/editar"              // 10b

    // Extras
    const val HISTORIAL = "historial"                      // 11
    const val PUNTOS = "puntos"                            // 12
    const val NOTIFICACIONES = "notificaciones"            // 13
}