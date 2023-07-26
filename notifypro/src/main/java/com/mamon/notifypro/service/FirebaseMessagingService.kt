package com.mamon.notifypro.service

import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService
import com.mamon.notifypro.model.Notification
import com.mamon.notifypro.utils.*
import com.mamon.notifypro.utils.TypeConverter.stringToNotifyObject

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

class FirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notify: Notification
    private lateinit var notifyManager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val sharePref = SharedPreferencesManager(applicationContext)
        sharePref.saveToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        notify = stringToNotifyObject(remoteMessage.data[Constants.NOTIFICATION])
        notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notifyManager.createNotification(applicationContext,notify)
    }


}


