package com.workmanagerexample.app

import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    var random: Random = Random()
    var randomNum = random.nextBoolean()

    override fun doWork(): Result {

        val valueA = inputData.getString("keyA")
        val valueB = inputData.getInt("keyB", 0)

        var intent: Intent = Intent(applicationContext, MyService::class.java)
        applicationContext.startService(intent)

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