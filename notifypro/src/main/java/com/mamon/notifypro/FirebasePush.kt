package com.mamon.notifypro

import com.mamon.notifypro.config.PushType.TO_GROUP
import com.mamon.notifypro.config.PushType.TO_TOKEN
import com.mamon.notifypro.config.PushType.TO_TOPIC
import com.mamon.notifypro.connection.PushNotificationTask
import com.mamon.notifypro.model.Notification
import com.mamon.notifypro.service.PushService
import com.mamon.notifypro.utils.Constants
import com.mamon.notifypro.utils.PackageDirectory
import com.mamon.notifypro.utils.TypeConverter.notifyObjectToString
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

class FirebasePush() :PushService, FirebasePushBuilder {

    private lateinit var serverKey: String
    private var notification: Notification = Notification()
    private var root = JSONObject()
    var asyncResponse: PushNotificationTask.AsyncResponse? = null


    fun build(serverKey: String, mainPackage: String) {
        this.serverKey = serverKey
        PackageDirectory.setPackage(mainPackage)
    }


    override fun sendToTopic(topic: String) {
        root.put(TO_TOPIC, "'$topic' in topics")
        sendPushNotification(true)
    }

    override fun sendToGroup(mobileTokens: JSONArray) {
        root.put(TO_GROUP, mobileTokens)
        sendPushNotification(false)
    }

    override fun sendToToken(token: String) {
        root.put(TO_TOKEN, token)
        sendPushNotification(false)
    }

    override fun setNotification(notification: Notification) = apply {
        this.notification = notification
    }


    override fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) = apply {
        this.asyncResponse = asyncResponse
    }

    override fun setOnFinishPush(onFinishPush: () -> Unit) = apply {
        this.asyncResponse = object : PushNotificationTask.AsyncResponse {
            override fun onFinishPush(outPut: String) {
                onFinishPush()
            }
        }
    }


    private fun sendPushNotification(toTopic: Boolean) {
        val httpConnection = URL(Constants.API_URL_FCM).openConnection() as HttpURLConnection
        httpConnection.apply {
            useCaches = false
            doInput = true
            doOutput = true
            requestMethod = Constants.POST

            setRequestProperty(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
            setRequestProperty(Constants.ACCEPT, Constants.APPLICATION_JSON)
            setRequestProperty(Constants.AUTHORIZATION, "key=$serverKey")
        }

        root.put(Constants.NOTIFICATION, notification.toJSONObject())
        root.put(Constants.DATA, JSONObject().put(Constants.NOTIFICATION, notifyObjectToString(notification)))

        val pushNotificationTask = PushNotificationTask(httpConnection, root, toTopic)
        pushNotificationTask.asyncResponse = asyncResponse
        pushNotificationTask.execute()
    }

}
