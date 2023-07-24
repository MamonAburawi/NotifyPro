package com.mamon.notifypro.utils

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

fun getToken(onComplete:(String)-> Unit){
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("Main", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
        val token = task.result
        Log.d("Main", "Token: $token")
        onComplete(token)

    })
}
