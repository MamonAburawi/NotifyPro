package com.mamon.notifypro.connection

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.HttpURLConnection

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */


class PushNotificationTask(private val conn: HttpURLConnection,
                           private val root: JSONObject,
                           private val toTopic: Boolean) {

    interface AsyncResponse {
        fun onFinishPush(outPut: String)
    }

    var asyncResponse: AsyncResponse? = null

    fun execute() {
        AsyncTask.execute {
            var wr: OutputStreamWriter? = null
            try {
                wr = OutputStreamWriter(conn.outputStream)
                wr.write(root.toString())
                wr.flush()

                val builder = StringBuilder()

                conn.inputStream.bufferedReader().use { reader ->
                    builder.append(reader.readLine())
                }

                val obj = JSONObject(builder.toString())
                val success = obj.getInt("success")
                val output = if (toTopic && obj.has("message_id") || !toTopic && success > 0) {
                    "SUCCESS"
                } else {
                    builder.toString()
                }

                asyncResponse?.onFinishPush(output)
            } catch (e: Exception) {
                Log.e("PushNotification", e.message, e)
                asyncResponse?.onFinishPush("Error in post to ${e.message}")
            } finally {
                wr?.close()
            }
        }
    }
}
