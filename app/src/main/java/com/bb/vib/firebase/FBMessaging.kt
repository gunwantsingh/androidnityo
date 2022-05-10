package com.bb.vib.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bb.vib.R
import com.bb.vib.ui.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FBMessaging : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("#####", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("#####", "From: ${remoteMessage.from}")

        var mTitle = ""
        var mBody = ""
        var mUniqueNotificationCode = ""
        var mType = ""

        remoteMessage.data.let {
            Log.d("#####", "Message data payload: " + remoteMessage.data)
            mUniqueNotificationCode = "${it["Data"]}"
            mType = "${it["Type"]}"
        }

        remoteMessage.notification?.let {
            Log.d("#####", "Message Notification Body: ${it.body}")
            mBody = "${it.body}"
            mTitle = "${it.title}"
        }
        mShowNotification(mTitle, mBody, mUniqueNotificationCode, mType)

    }

    private fun mShowNotification(
        mTitle: String,
        mBody: String,
        mUniqueNotificationCode: String,
        mType: String
    ) {

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        intent.putExtra("uniqueNotificationCode", mUniqueNotificationCode)
        intent.putExtra("type", mType)


        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(mTitle)
            setContentText(mBody)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setSmallIcon(R.mipmap.ic_vib_launcher_round)

            val bigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.setBigContentTitle(mTitle)
            bigTextStyle.bigText(mBody)
            setStyle(bigTextStyle)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//        if (mTitle == com.bb.vib.api.Constants.NOTIFICATION_TYPE_CREDIT|| mTitle == com.bb.vib.api.Constants.NOTIFICATION_TYPE_DEBIT) {
//            sendBroadcast(Intent().apply { action = "${com.bb.vib.api.Constants.NOTIFICATION_ACTION}${mTitle}" })
//        }


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())

    }

}


