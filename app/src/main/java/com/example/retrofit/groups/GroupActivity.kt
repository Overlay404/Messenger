package com.example.retrofit.groups

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.*
import com.example.retrofit.addGroups.AddNewGroups
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupActivity : AppCompatActivity() {
    val courseModelArrayList = ArrayList<Group>()
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        val image: ImageView = findViewById(R.id.imageView)
        val text: TextView = findViewById(R.id.textView3)
        var buttonNew: Button = findViewById(R.id.addGroups)
        val code = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.158:8080//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: EchoController = retrofit.create(EchoController::class.java)
        val call: Call<List<Groups>> = service.showGroups(code)
        val callVerify: Call<Boolean> = service.verification(code)
        callVerify.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val answer: Boolean? = response.body()
                if(answer != true){
                    val intentRegistration = Intent(this@GroupActivity, Registration::class.java)
                    intentRegistration.putExtra("code", code)
                    startActivity(intentRegistration)
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("verificationError", "ошибка верификации пользователя")
            }
        })
        call.enqueue(object: Callback<List<Groups>> {
            override fun onResponse(call: Call<List<Groups>>, response: Response<List<Groups>>) {
                val answer: List<Groups>? = response.body()
                if (answer != null) {
                    for(item in answer){
                        courseModelArrayList.add(Group(item.id,item.name))
                    }
                }
                if(courseModelArrayList.size == 0){
                    text.visibility = VISIBLE;
                    image.visibility = VISIBLE;
                }else{
                    text.visibility = GONE;
                    image.visibility = GONE;
                }
            }

            override fun onFailure(call: Call<List<Groups>>, t: Throwable) {
                Log.d("groupShowError", "ошибка при заполнении группами")
            }

        })

        this.giveAdapter()

        buttonNew.setOnClickListener {
            var intent = Intent(this, AddNewGroups::class.java)
            startActivity(intent)
        }
    }

    private fun giveAdapter(){
        Thread.sleep(500L)
        val recyclerGroups:RecyclerView = findViewById(R.id.recyclerGroups)
        val clickListener: AdapterGroup.OnClickListener = object : AdapterGroup.OnClickListener {
            @Override
            override fun onGroupClick(group: Group, position: Int) {
                val intent = Intent(this@GroupActivity, MainActivity::class.java)
                intent.putExtra("id", group.id)
                startActivity(intent)
            }
        }
        val adapter = AdapterGroup(this,courseModelArrayList, clickListener)
        val linearLayout = LinearLayoutManager(this)
        recyclerGroups.layoutManager = linearLayout
        recyclerGroups.adapter = adapter
    }
}