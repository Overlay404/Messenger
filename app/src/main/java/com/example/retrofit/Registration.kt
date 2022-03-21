package com.example.retrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.retrofit.groups.GroupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val name: EditText = findViewById(R.id.nameUser)
        val next: Button = findViewById(R.id.next)

        next.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.158:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service: EchoController = retrofit.create(EchoController::class.java)

            val name = name.text.toString()

            val call: Call<Boolean> = service.addUser(intent.getSerializableExtra("code").toString(), name)
            call.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val answer: Boolean? = response.body()
                    Log.d("Controller", "ответ от сервера получен: $answer")
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("Controller", "ошибка")
                }

            })
            val intentMain = Intent(this, GroupActivity::class.java)
            startActivity(intentMain)
        }
    }
}