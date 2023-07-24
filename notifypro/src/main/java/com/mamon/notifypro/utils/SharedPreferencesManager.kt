package com.mamon.notifypro.utils

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService

object SharedPreferencesManager {

    fun saveToken(context: Context, token:String?){
        context.getSharedPreferences(Constants.SHARED_PREFERNCE_NAME, FirebaseMessagingService.MODE_PRIVATE).edit().putString(
            Constants.TOKEN, token).apply()
    }

    fun getToken(context: Context):String?{
        return context.getSharedPreferences(Constants.SHARED_PREFERNCE_NAME, FirebaseMessagingService.MODE_PRIVATE).getString(
            Constants.TOKEN, null)
    }

}
