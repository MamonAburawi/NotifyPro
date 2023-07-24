package com.mamon.notifypro

import com.mamon.notifypro.config.PushType.TO_GROUP
import com.mamon.notifypro.config.PushType.TO_TOKEN
import com.mamon.notifypro.config.PushType.TO_TOPIC
import com.mamon.notifypro.connection.PushNotificationTask
import com.mamon.notifypro.model.Notification
import com.mamon.notifypro.service.PushService
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

class FirebasePush() :PushService, FirebasePushBuilder {
    private lateinit var serverKey: String


    /** Build method **/
    fun build(serverKey: String) {
        this.serverKey = serverKey
    }


    companion object {

        /** Request method **/
        private const val POST = "POST"

        /** Properties keys **/
        private const val CONTENT_TYPE = "Content-Type"
        private const val ACCEPT = "Accept"
        private const val AUTHORIZATION = "Authorization"

        /** Properties values **/
        private const val APPLICATION_JSON = "application/json"

        /** Root keys **/
        private const val NOTIFICATION = "notification"
        private const val DATA = "data"

        /** FCM **/
        private const val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
    }

    var notification: Notification = Notification()
        private set


    var data = JSONObject()
        private set

    private var root: JSONObject = JSONObject()
    var asyncResponse: PushNotificationTask.AsyncResponse? = null

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

    override fun setData(data: JSONObject) = apply {
        this.data = data
    }

    override fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) = apply {
        this.asyncResponse = asyncResponse
    }

    override fun setOnFinishPush(onFinishPush: () -> Unit) = apply {
        this.asyncResponse = object : PushNotificationTask.AsyncResponse {
            override fun onFinishPush(ouput: String) {
                onFinishPush()
            }
        }
    }

    private fun sendPushNotification(toTopic: Boolean) {
        val conn = URL(API_URL_FCM).openConnection() as HttpURLConnection
        conn.apply {
            useCaches = false
            doInput = true
            doOutput = true
            requestMethod = POST

            setRequestProperty(CONTENT_TYPE, APPLICATION_JSON)
            setRequestProperty(ACCEPT, APPLICATION_JSON)
            setRequestProperty(AUTHORIZATION, "key=$serverKey")
        }


        root.put(NOTIFICATION, notification.toJSONObject())
        root.put(DATA, data)

        val pushNotificationTask = PushNotificationTask(conn, root, toTopic)
        pushNotificationTask.asyncResponse = asyncResponse
        pushNotificationTask.execute()
    }
}
//package com.mamon.notifypro
//
//import com.mamon.notifypro.config.PushType.TO_GROUP
//import com.mamon.notifypro.config.PushType.TO_TOKEN
//import com.mamon.notifypro.config.PushType.TO_TOPIC
//import com.mamon.notifypro.connection.PushNotificationTask
//import com.mamon.notifypro.model.Notification
//import com.mamon.notifypro.service.PushService
//import org.json.JSONArray
//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL
//
///**
// * Created by Mamon Aburawi on 24/7/2023.
// */
//
//class FirebasePush constructor(private val serverKey: String) :PushService, FirebasePushBuilder {
//
//    companion object {
//
//        /** Request method **/
//        private const val POST = "POST"
//
//        /** Properties keys **/
//        private const val CONTENT_TYPE = "Content-Type"
//        private const val ACCEPT = "Accept"
//        private const val AUTHORIZATION = "Authorization"
//
//        /** Properties values **/
//        private const val APPLICATION_JSON = "application/json"
//
//        /** Root keys **/
//        private const val NOTIFICATION = "notification"
//        private const val DATA = "data"
//
//        /** Build method **/
//        fun build(serverKey: String) = FirebasePush(serverKey)
//
//        /** FCM **/
//        private const val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
//    }
//
//    var notification: Notification = Notification()
//        private set
//
//
//    var data = JSONObject()
//        private set
//
//    private var root: JSONObject = JSONObject()
//    var asyncResponse: PushNotificationTask.AsyncResponse? = null
//
//    override fun sendToTopic(topic: String) {
//        root.put(TO_TOPIC, "'$topic' in topics")
//        sendPushNotification(true)
//    }
//
//    override fun sendToGroup(mobileTokens: JSONArray) {
//        root.put(TO_GROUP, mobileTokens)
//        sendPushNotification(false)
//    }
//
//    override fun sendToToken(token: String) {
//        root.put(TO_TOKEN, token)
//        sendPushNotification(false)
//    }
//
//    override fun setNotification(notification: Notification) = apply {
//        this.notification = notification
//    }
//
//    override fun setData(data: JSONObject) = apply {
//        this.data = data
//    }
//
//    override fun setOnFinishPush(asyncResponse: PushNotificationTask.AsyncResponse) = apply {
//        this.asyncResponse = asyncResponse
//    }
//
//    override fun setOnFinishPush(onFinishPush: () -> Unit) = apply {
//        this.asyncResponse = object : PushNotificationTask.AsyncResponse {
//            override fun onFinishPush(ouput: String) {
//                onFinishPush()
//            }
//        }
//    }
//
//    private fun sendPushNotification(toTopic: Boolean) {
//        val conn = URL(API_URL_FCM).openConnection() as HttpURLConnection
//        conn.apply {
//            useCaches = false
//            doInput = true
//            doOutput = true
//            requestMethod = POST
//
//            setRequestProperty(CONTENT_TYPE, APPLICATION_JSON)
//            setRequestProperty(ACCEPT, APPLICATION_JSON)
//            setRequestProperty(AUTHORIZATION, "key=$serverKey")
//        }
//
//
//        root.put(NOTIFICATION, notification.toJSONObject())
//        root.put(DATA, data)
//
//        val pushNotificationTask = PushNotificationTask(conn, root, toTopic)
//        pushNotificationTask.asyncResponse = asyncResponse
//        pushNotificationTask.execute()
//    }
//}