package com.workmanagerexample.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myData: Data = Data.Builder()
            .putString("keyA", "value777777777")
            .putInt("keyB", 1)
            .build()

        val myWorkRequest =
            OneTimeWorkRequest.Builder(MyWorker::class.java)
                        .setInitialDelay(10, java.util.concurrent.TimeUnit.SECONDS)
                .setInputData(myData)
                .build()
//                PeriodicWorkRequest.Builder(MyWorker::class.java, 5, java.util.concurrent.TimeUnit.SECONDS)
//                        .setInputData(myData)
//                        .build()
        WorkManager.getInstance().enqueue(myWorkRequest)

        WorkManager.getInstance().getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, {
            println("!!!!!!!!!!!!!!!" + it.state)
            println("**************" + it.outputData.getString("keyC"))
        })
    }
}