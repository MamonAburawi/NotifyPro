# NotifyPro

Notify Pro is a library that allows developers to easily implement push notifications in their Android apps using Firebase Cloud Messaging (FCM).

## Demo

![animation](https://github.com/MamonAburawi/NotifyPro/assets/61309294/21cf9166-08ed-40f2-abbf-d71125bf6360)


## Notification

<img src="https://github.com/MamonAburawi/NotifyPro/assets/61309294/9971cda5-6683-4683-88f3-69bd0e46fa94" width="250" height="400"> 

1- Title.

2- Description.

3- Small Icon.

4- Large Icon



# Implementation

Step 1. Add it to your root build.gradle at the end of repositories:

```sh
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```



Step 2. Add the dependency:

```sh
dependencies {
    implementation 'com.github.MamonAburawi:NotifyPro:${lastVersion}'   
 }
```




Step 3. Make sure you are connected with Firebase and add the JSON file correctly.

Step 4. Enable the cloud messaging API -> Go to Project Settings > Cloud Messaging > Click on three dots then on Manage API in the google cloud console 

<img src="https://github.com/MamonAburawi/NotifyPro/assets/61309294/1c9cd679-e876-4dc5-83f6-d83a9f507edd" width="1000" height="500"> 

Step 5. Enable the API

<img src="https://github.com/MamonAburawi/NotifyPro/assets/61309294/72cc2b30-7736-4da5-9359-549c769addb0" width="1000" height="500"> 


Step 6. init the fireBasePush oject the pass the serverKey and packageName as parameters

```sh
val fireBasePush = FirebasePush()
fireBasePush.build(serverKey,packageName)
```

NOTE: you can generate the server key from Cloud Messaging API (Legacy) section.


Android 12 and Up 
To ensure that you receive notifications from the app, please make sure that notifications are enabled in the app settings. If you are using an Android device with version 12 or higher, it is important to also check that the app has permission to POST_NOTIFICATION. This can usually be done in the app settings or by checking the device's notification settings for the app. By enabling notifications and granting the necessary permissions, you can stay up-to-date with the latest news and information from the app.

```sh
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

```



## How get Token

you can use this simple function to get your token from the server

```sh
   getToken { token ->   }
```

## How Push Notification 

```sh
   val title = "Notify Pro"
   val description = "Please notify me once you have news!"
   val smallIcon = getDrawableName(R.drawable.ic_no_connection)
   val largeIcon = getDrawableName(R.drawable.ic_notify)
   val token = "token here"

        val notification = Notification(
            title = title,
            description = description,
            smallIcon = smallIcon,
            largeIcon = largeIcon
        )
        fireBasePush.setNotification(notification)
        fireBasePush.sendToToken(token)
```

NOTE: you have to use the getDrawableName() function that allows you to return the name of an icon only because the library will use this name to find the specific icon later in onReceiveMessage to display it in the notification.

```sh
  getDrawableName()
```


## Enjoy



