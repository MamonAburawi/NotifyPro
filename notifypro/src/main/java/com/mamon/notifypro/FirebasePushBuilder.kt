package com.mamon.notifypro

import com.mamon.notifypro.connection.PushNotificationTask
import com.mamon.notifypro.model.Notification
import org.json.JSONObject

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

interface FirebasePushBuilder {
    fun setNotification(notification: Notification) : FirebasePush
    fun setData(data: JSONObject) : FirebasePush
    fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) : FirebasePush
    fun setOnFinishPush(onFinishPush: () -> Unit) : FirebasePush
}