package com.workmanagerexample.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "com.workmanagerexample.app.action.COM") {
            Toast.makeText(
                context, "Обнаружено сообщение: " +
                        intent?.getStringExtra("com.workmanagerexample.app.broadcast.Message"),
                Toast.LENGTH_LONG
            ).show();
        }
    }
}