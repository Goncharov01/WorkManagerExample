package com.workmanagerexample.app

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    var random: Random = Random()
    var randomNum = random.nextBoolean()

    override fun doWork(): Result {

        println("DO WORK !!!!!!!!!!!!!!!!!!!!!!")

        val valueA = inputData.getString("keyA")
        val valueB = inputData.getInt("keyB", 0)

        var intent: Intent = Intent(applicationContext, MyService::class.java)
        applicationContext.startService(intent)

        var broadcastReceiver: MyBroadcastReceiver = MyBroadcastReceiver()
        applicationContext.registerReceiver(
            broadcastReceiver,
            IntentFilter("com.workmanagerexample.app.action.COM")
        )

        var intent2: Intent = Intent()
        intent2.setAction("com.workmanagerexample.app.action.COM")
        intent2.putExtra("com.workmanagerexample.app.broadcast.Message", "MyMessage")
        applicationContext.sendBroadcast(intent2)

        val o: Data = Data.Builder()
            .putString("keyC", "value11")
            .putInt("keyD", 11)
            .build()

        if (true) {
            return Result.success(o)
        } else {
            return Result.failure()
        }

    }


}