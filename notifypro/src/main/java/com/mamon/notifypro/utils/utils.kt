package com.mamon.notifypro.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await


fun getToken(onComplete:(String)-> Unit) {
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

suspend fun getToken(): String = FirebaseMessaging.getInstance().token.await()

object PackageDirectory {
    private var mPackage: String = ""

    fun setPackage(pack: String){
        mPackage = pack
    }

    /** return the main package of app **/
    fun getPackage() = mPackage
}


fun Context.getDrawableResource(icName: String): Int {
    val packageName = PackageDirectory.getPackage()
    return  resources.getIdentifier(icName, "drawable", packageName)
}


fun Context.getDrawableName(drawableId: Int): String {
    val context = createPackageContext(PackageDirectory.getPackage(), 0)
    val resourceName = context.resources.getResourceEntryName(drawableId)
    val parts = resourceName.split(".")
    return parts.lastOrNull() ?: "" // e.g. "ic_no_connection"
}






