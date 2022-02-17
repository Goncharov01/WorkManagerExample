package com.workmanagerexample.app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.workmanagerexample.app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var actionCom: String = "com.workmanagerexample.app.action.COM"
    var alarmMessage: String = "BroadcastResiverCOM"

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var userList: List<MyUsers>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        loadUser()
        val db = Firebase.firestore
        val testListUser = mutableListOf<MyUsers>()

        userAdapter = UserAdapter(userList)
        binding.list.adapter = userAdapter

//        var userAdapter: UserAdapter = UserAdapter(testListUser)

//        binding.list.adapter = userAdapter

//        binding.buttonSave.setOnClickListener(View.OnClickListener {
//            db.collection("users")
//                .add(
//                    MyUsers(
//                        binding.editTextTextFirstName.text.toString(),
//                        binding.editTextTextLastName.text.toString(),
//                        binding.editTextTextBorn.text.toString().toInt()
//                    )
//                )
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error adding document", e)
//                }
//        })


        //READ DATA FIRESTORE
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                var mutableListUsers: MutableList<MyUsers> = mutableListOf()

                for (document in result) {
                    mutableListUsers.add(document?.toObject(MyUsers::class.java)!!)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                println("!!!!!!" + mutableListUsers)
//                userAdapter.addData(mutableListUsers)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

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

    private fun loadUser() {
        userList = listOf(
            MyUsers("Vlad", "Wardon", 18),
            MyUsers("Boris", "Holov", 25),
            MyUsers("Nana", "Urid", 35),
        )
    }

}