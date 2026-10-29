package mx.itson.hogarea.ui.extras

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import mx.itson.hogarea.MainActivity

private const val CANAL = "recordatorios"

// 13b · Muestra en el teléfono el recordatorio "Te toca: Barrer la sala"
fun mostrarRecordatorio(contexto: Context) {
    // Desde Android 13 hace falta el permiso; si no lo dieron, no se muestra nada
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(contexto, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    // Las notificaciones van en un canal. Crearlo otra vez no hace nada, así que no hay que revisar si ya existe.
    val canal = NotificationChannel(CANAL, "Recordatorios de tareas", NotificationManager.IMPORTANCE_DEFAULT)
    contexto.getSystemService(NotificationManager::class.java).createNotificationChannel(canal)

    // Al tocar la notificación se abre la app
    val abrirApp = Intent(contexto, MainActivity::class.java)
    val alTocar = PendingIntent.getActivity(contexto, 0, abrirApp, PendingIntent.FLAG_IMMUTABLE)

    val notificacion = NotificationCompat.Builder(contexto, CANAL)
        .setSmallIcon(android.R.drawable.ic_popup_reminder)
        .setContentTitle("Te toca: Barrer la sala")
        .setContentText("Casa Nainari · hoy jueves")
        .setContentIntent(alTocar)
        .setAutoCancel(true)
        .build()
    NotificationManagerCompat.from(contexto).notify(1, notificacion)
}