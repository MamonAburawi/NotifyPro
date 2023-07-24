package com.mamon.notifypro.service

import org.json.JSONArray

/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

interface PushService {
    fun sendToTopic(topic: String)
    fun sendToGroup(mobileTokens: JSONArray)
    fun sendToToken(token: String)
}