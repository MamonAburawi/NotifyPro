package com.mamon.notifypro.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mamon.notifypro.model.Notification

object TypeConverter {

    fun stringToNotifyObject(data: String?): Notification {
        val listType = object : TypeToken<Notification>() {}.type
        val gson = Gson()
        return gson.fromJson(data, listType)
    }


    fun notifyObjectToString(notification: Notification): String {
        val gson = Gson()
        val listType = object : TypeToken<Notification>() {}.type
        return gson.toJson(notification, listType)
    }

}