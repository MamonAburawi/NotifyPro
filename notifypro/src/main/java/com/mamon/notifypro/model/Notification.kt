package com.mamon.notifypro.model

import org.json.JSONObject

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

data class Notification (
    var title: String? = null,
    var description: String? = null,
    var smallIcon: String? = null,
    var largeIcon: String? = null){

    fun toJSONObject(): JSONObject {
        return JSONObject().apply {
            put("title", title)
            put("description", description)
            put("smallIcon", smallIcon)
            put("largeIcon", largeIcon)
        }
    }
}

