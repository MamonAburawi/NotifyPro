package com.mamon.notifypro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mamon.notifypro.databinding.ActivityMainBinding
import com.mamon.notifypro.utils.*
import com.mamon.notifypro.model.Notification


/**
 * Created by Mamon Aburawi on 24/7/2023.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fireBasePush = FirebasePush()
    val serverKey = "AAAAKum8IiU:APA91bGZVExHnYXljirVasYAbZOPrn1puzkEpCKrGwPLPuOaEmRDFs-VhUNoIY1JkIjoVdQWhL1PIQkVYEyO_PvNMdtqr-GWfl17crludzlM_1Rxpn-jvYBUNQpxkqE_8Pf3mmaEXERj"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this
        binding.lifecycleOwner = this

        askNotificationPermission()

        fireBasePush.build(serverKey,packageName)


        getToken { token ->
            binding.token.setText(token)
        }


    }

    fun onSendClick() {

        val title = binding.title.text?.trim().toString()
        val description = binding.message.text?.trim().toString()
        val smallIcon = getDrawableName(R.drawable.ic_no_connection)
        val largeIcon = getDrawableName(R.drawable.ic_notify)

        val token = binding.token.text?.trim().toString()


        val notification = Notification(
            title = title,
            description = description,
            smallIcon = smallIcon,
            largeIcon = largeIcon
        )
        fireBasePush.setNotification(notification)
        fireBasePush.sendToToken(token)
        fireBasePush.setOnFinishPush {
            Toast.makeText(this,"send",Toast.LENGTH_SHORT).show()

        }

    }



    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }




}