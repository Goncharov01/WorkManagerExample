package com.workmanagerexample.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.workmanagerexample.app.databinding.ActivityMainBinding
import org.w3c.dom.Document

class MainActivity : AppCompatActivity() {

    var actionCom: String = "com.workmanagerexample.app.action.COM"
    var alarmMessage: String = "BroadcastResiverCOM"

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var userList: MutableList<MyUsers> = mutableListOf()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(userList)
        binding.list.adapter = userAdapter

        val s = getString(R.string.welcome_messages, "sasha")
        binding.textView.text = s

        val db = Firebase.firestore

        binding.buttonSave.setOnClickListener(View.OnClickListener {
            db.collection("users").document("1")
                .set(
                    mapOf(
                        "id" to db.collection("users").document().id,
                        "first" to binding.editTextTextFirstName.text.toString(),
                        "last" to binding.editTextTextLastName.text.toString(),
                        "born" to binding.editTextTextBorn.text.toString().toInt()
                    )
                )
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        })


        //READ DATA FIRESTORE
        db.collection("users").document("1")
            .addSnapshotListener { snapshot, e ->

                val mutableListUsers: MutableList<MyUsers> = mutableListOf()

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: ${snapshot.data}")
                    println("!!!!!!" + snapshot.toObject(MyUsers::class.java))
                    mutableListUsers.add(snapshot?.toObject(MyUsers::class.java)!!)

                } else {
                    Log.d(TAG, "Current data: null")
                    if (e != null) {
                        println("@@@@@@" + e.message)
                    }
                }
                userAdapter.addData(mutableListUsers)
            }
//            .get()
//            .addOnSuccessListener { result ->
//                val mutableListUsers: MutableList<MyUsers> = mutableListOf()
//
//                for (document in result) {
//                    mutableListUsers.add(document?.toObject(MyUsers::class.java)!!)
//                    Log.d(TAG, "${document.id} => ${document.data}")
//                }
//                userAdapter.addData(mutableListUsers)
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }

        val myData: Data = Data.Builder()
            .putString("keyA", "value777777777")
            .putInt("keyB", 1)
            .build()

        val myWorkRequest =
            OneTimeWorkRequest.Builder(MyWorker::class.java)
//                .setInitialDelay(10, java.util.concurrent.TimeUnit.SECONDS)
                .setInputData(myData)
                .build()
//                PeriodicWorkRequest.Builder(MyWorker::class.java, 5, java.util.concurrent.TimeUnit.SECONDS)
//                        .setInputData(myData)
//                        .build()
        WorkManager.getInstance().enqueue(myWorkRequest)

//        WorkManager.getInstance().getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, {
//            println("!!!!!!!!!!!!!!!" + it.state)
//            println("**************" + it.outputData.getString("keyC"))
//        })
    }
}
