package com.example.registrodeactividades

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class DataResetReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID = 1
    }
    override fun onReceive(context: Context, intent: Intent?) {
        createSimpleNotification(context)
    }

    private fun createSimpleNotification(context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(
            context, "myChannel"
        )
            .setSmallIcon(R.drawable.actividad_register_icon)
            .setContentTitle("Tomar agua")
            .setContentText("Toma tu agua ahorita")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Despues no te quejes, porque ya no se considera despues, es como si no huebiras tomado."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setColor(ContextCompat.getColor(context, R.color.purple_200))
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)

    }
}
