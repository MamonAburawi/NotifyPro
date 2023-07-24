package com.mamon.notifypro.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.mamon.notifypro.R
import kotlin.random.Random

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

class FirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FireBaseService"
        private const val channelId = "CHANNEL_ID"
        private const val channelName = "Name"
    }



    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", token)
//        SharedPreferencesManager.saveToken(applicationContext, token)
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val notification = remoteMessage.notification

        val title = notification?.title?: ""
        val text = notification?.body?: ""
        val icon = notification?.icon


        Log.d(
            TAG," \n" +
                "title: $title " +
                "\n text: $text" +
                "\n icon: $icon")


        createNotification(title,text)


    }


    fun createNotification(title: String, description: String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(com.google.android.material.R.drawable.ic_keyboard_black_24dp)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification = builder.build()

        notificationManager.notify(notificationID, notification)


    }







}


