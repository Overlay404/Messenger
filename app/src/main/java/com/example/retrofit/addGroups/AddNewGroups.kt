package com.example.retrofit.addGroups

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.*
import com.example.retrofit.groups.AdapterGroup
import com.example.retrofit.groups.Group
import com.example.retrofit.groups.GroupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddNewGroups : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_groups)
        var nameUserList = ArrayList<UserList>()
        var buttonNewGroup: Button = findViewById(R.id.buttonNewGroup)
        var nameGroup: EditText = findViewById(R.id.nameGroup)
        var intArray = intArrayOf(10);
        var usersCodeList = ArrayList<String>()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.158:8080//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: EchoController = retrofit.create(EchoController::class.java)
        val call: Call<List<UserName>> = service.showAllUser()
        call.enqueue(object: Callback<List<UserName>> {
            override fun onResponse(call: Call<List<UserName>>, response: Response<List<UserName>>) {
                val answer: List<UserName>? = response.body()
                if (answer != null) {
                    for(item in answer){
                        nameUserList.add(UserList(item.id,item.name,item.code))
                    }
                }
            }
            override fun onFailure(call: Call<List<UserName>>, t: Throwable) {
                Log.d("AddNewGroupsOutput", "Ошибка в выводе пользователей")
            }
        })
        Thread.sleep(500L)
        val recyclerGroups: RecyclerView = findViewById(R.id.RecyclerUser)
        val clickListener: AdapterAddGroup.OnClickListener = object : AdapterAddGroup.OnClickListener {
            @Override
            override fun onGroupClick(userList: UserList, position: Int) {
                usersCodeList.add(userList.code)
            }
        }


        buttonNewGroup.setOnClickListener {
            val callNewGroups: Call<Boolean> = service.addGroups(usersCodeList, nameGroup.text.toString())
            callNewGroups.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val answer: Boolean? = response.body()
                    Log.d("newGroupRetrofit", answer.toString())
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("newGroupRetrofit", "false")
                }
            })
            var intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
        }
            val adapter = AdapterAddGroup(this,nameUserList, clickListener)
            val linearLayout = LinearLayoutManager(this)
            recyclerGroups.layoutManager = linearLayout
            recyclerGroups.adapter = adapter
        }
    }
