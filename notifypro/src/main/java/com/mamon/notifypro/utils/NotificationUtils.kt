package com.mamon.notifypro.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mamon.notifypro.R
import com.mamon.notifypro.model.Notification
import kotlin.random.Random


fun NotificationManager.createNotification(context: Context,notify: Notification) {
    val resources = context.resources
    val notificationID = Random.nextInt()
    val largeIconBitmap = BitmapFactory.decodeResource(resources, context.getDrawableResource(notify.largeIcon?: ""))
    val smallIconRes = notify.smallIcon
    val smallIcon = if (smallIconRes != null) context.getDrawableResource(smallIconRes) else R.drawable.ic_notification


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        .setSmallIcon(smallIcon)
        .setContentTitle(notify.title)
        .setContentText(notify.description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // set large icon
    if (largeIconBitmap !=  null){
        builder.setStyle(
            NotificationCompat.BigPictureStyle()
            .bigPicture(largeIconBitmap)
            .bigLargeIcon(null))
    }

    val notification = builder.build()

    notify(notificationID, notification)
}

