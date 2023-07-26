package com.mamon.notifypro.utils

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService

class SharedPreferencesManager(context: Context) {
    private val sharePref = context.getSharedPreferences(Constants.SHARED_PREFERENCE, FirebaseMessagingService.MODE_PRIVATE)
    private val editor = sharePref.edit()

    fun saveToken(token:String?) {
        editor.putString(Constants.TOKEN, token).apply()
    }

    fun getToken() = sharePref.getString(Constants.TOKEN, null)
}
