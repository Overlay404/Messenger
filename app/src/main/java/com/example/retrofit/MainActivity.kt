package com.example.retrofit

import android.annotation.SuppressLint
import android.graphics.Color.rgb
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@SuppressLint("HardwareIds")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val idGroup = intent.getSerializableExtra("id");
        val field: EditText = findViewById(R.id.field)
        val add: Button = findViewById(R.id.add)
        val show: Button = findViewById(R.id.show)
        val code = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        var nameUser = ""
        val linear: LinearLayout = findViewById(R.id.Linear)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.158:8080//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: EchoController = retrofit.create(EchoController::class.java)
        val checkUserNameCall: Call<List<UserName>> = service.showUser(code)
        checkUserNameCall.enqueue(object : Callback<List<UserName>> {
            override fun onResponse(
                call: Call<List<UserName>>,
                response: Response<List<UserName>>
            ) {
                val answer: List<UserName>? = response.body()
                if (answer != null) {
                    for (item in answer)
                        nameUser = item.name
                    Log.d("!!!!", nameUser)
                }
            }

            override fun onFailure(call: Call<List<UserName>>, t: Throwable) {
                Log.d("Error!", "Check user name in MainActivity")
            }
        })
        add.setOnClickListener {
            val callMessage: Call<Boolean> = service.addMessage(
                field.text.toString(),
                idGroup as Int,
                nameUser
            )
            callMessage.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    Log.d("Ok", response.body().toString())
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("Error", "Add new message in MainActivity")
                }
            })
            field.setText("")
        }
    show.setOnClickListener{
        val callMessageList: Call<List<Message>> = service.showMessage(idGroup as Int)
        callMessageList.enqueue(object : Callback<List<Message>> {
            override fun onResponse(
                call: Call<List<Message>>,
                response: Response<List<Message>>
            ) {
                val answer: List<Message>? = response.body()
                if (answer != null) {
                    if (linear.parent != null) {
                        linear.removeAllViews()
                    }
                    for (item in answer) {
                        if (item.name == nameUser) {
                            val card = CardView(this@MainActivity)
                            card.radius = 30f
                            card.setContentPadding(20, 10, 10, 20)
                            card.setCardBackgroundColor(rgb(93, 93, 180))
                            card.addView(generateTextViewName(item.name))
                            card.addView(generateTextViewText(item.text))
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            layoutParams.setMargins(0, 0, 0, 10)
                            layoutParams.gravity = 5
                            card.layoutParams = layoutParams
                            linear.addView(card)
                        } else {
                            val card = CardView(this@MainActivity)
                            card.radius = 30F
                            card.setContentPadding(10, 10, 20, 20)
                            card.setCardBackgroundColor(rgb(208, 185, 180))
                            card.addView(generateTextViewName(item.name))
                            card.addView(generateTextViewText(item.text))
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            layoutParams.setMargins(0, 0, 0, 10)
                            card.layoutParams = layoutParams
                            linear.addView(card)
                        }
                    }
                }
            }


            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Log.d("Error", "add item in the arrayList in MainActivity")
            }
        })
    }
    }
    @SuppressLint("SetTextI18n")
    fun generateTextViewName(nameUser: String): TextView {
        val textView = TextView(this)
        textView.textSize = 12f
        textView.text = nameUser
        return textView
    }
    @SuppressLint("SetTextI18n")
    fun generateTextViewText(item: String): TextView {
        val textView = TextView(this)
        textView.textSize = 18f
        textView.text = "\n  $item"
        return textView
    }
}

//        showTextUserName(linear, code)
//
//        call.enqueue(object: Callback<Boolean>{
//            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                val answer: Boolean? = response.body()
//                if(answer != true){
//                    val intentRegistration = Intent(this@MainActivity, Registration::class.java)
//                    intentRegistration.putExtra("code", code)
//                    startActivity(intentRegistration)
//                }
//            }
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Log.d("Controller", "ошибка")
//            }
//        })
//
//        add.setOnClickListener {
//            if(lastname.text.toString() != "") {
//                addTextUserName(lastname,linear,code,scrollView)
//            }
//            else Toast.makeText(this, "Заполните поля данными", Toast.LENGTH_SHORT).show()
//
//        }
//        show.setOnClickListener {
//           showTextUserName(linear, code)
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun generateTextViewName(nameUser: String): TextView{
//        val textView = TextView(this)
//        textView.textSize = 10f
//        textView.text =  nameUser
//        return textView
//
//    }
//    @SuppressLint("SetTextI18n")
//    private fun generateTextViewText(item: String): TextView{
//        val textView = TextView(this)
//        textView.textSize = 18f
//        textView.text =  "\n$item"
//        return textView
//
//    }
//    private fun addTextUserName(lastname: TextView,linear: LinearLayout,code: String, scrollView: ScrollView){
//        var nameUser: String
//        val callShow: Call<List<UserName>> = service.userName()
//        callShow.enqueue(object: Callback<List<UserName>> {
//            override fun onResponse(
//                call: Call<List<UserName>>,
//                response: Response<List<UserName>>
//            ) {
//                val answer: List<UserName>? = response.body()
//                if (answer != null) {
//                    for(item in answer){
//                        if (item.code == code){
//                            nameUser = item.name
//                            addText(lastname, nameUser, scrollView, 0)
//                            showText(linear, nameUser)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserName>>, t: Throwable) {
//                Log.d("Controller", "ошибка")
//            }
//        })
//
//    }
//    private fun showTextUserName(linear: LinearLayout,code: String){
//        var nameUser: String
//        val callShow: Call<List<UserName>> = service.userName()
//        callShow.enqueue(object: Callback<List<UserName>> {
//            override fun onResponse(
//                call: Call<List<UserName>>,
//                response: Response<List<UserName>>
//            ) {
//                val answer: List<UserName>? = response.body()
//                if (answer != null) {
//                    for(item in answer){
//                        if (item.code == code){
//                            nameUser = item.name
//                            showText(linear, nameUser)
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserName>>, t: Throwable) {
//                Log.d("Controller", "ошибка")
//            }
//        })
//    }
//    private fun addText(lastname: TextView,nameUser: String, scrollView: ScrollView, index: Int){
//        val callAdd: Call<Boolean> = service.test(lastname.text.toString(), nameUser, index)
//        callAdd.enqueue(object: Callback<Boolean>{
//            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                val answer: Boolean? = response.body()
//                Log.d("Controller", "ответ получен $answer")
//            }
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Log.d("Controller", "ошибка")
//            }
//        })
//        scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight() + 10)
//        lastname.setText("")
//    }
//    private fun showText(linear: LinearLayout, nameUser: String){
//        val call: Call<List<Message>> = service.list()
//        call.enqueue(object: Callback<List<Message>>{
//            @SuppressLint("SetTextI18n")
//            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
//                val answer: List<Message>? = response.body()
//                if (answer != null) {
//
//                    if(linear.parent != null){
//                        linear.removeAllViews()
//                    }
//                    for (item in answer){
//                        if(item.name == nameUser) {
//                            val card = CardView(this@MainActivity)
//                            card.radius = 30f
//                            card.setContentPadding(20, 5, 10, 20)
//                            card.setCardBackgroundColor(rgb(8, 185, 180))
//                            card.addView(generateTextViewName(item.name))
//                            card.addView(generateTextViewText(item.text))
//                            val layoutParams = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                            )
//                            layoutParams.setMargins(0, 0, 0, 10)
//                            layoutParams.gravity = 5
//                            card.layoutParams = layoutParams
//                            linear.addView(card)
//                        }else {
//                            val card = CardView(this@MainActivity)
//                            card.radius = 15f
//                            card.setContentPadding(10, 5, 20, 20)
//                            card.setCardBackgroundColor(rgb(208, 185, 180))
//                            card.addView(generateTextViewName(item.name))
//                            card.addView(generateTextViewText(item.text))
//                            val layoutParams = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                            )
//                            layoutParams.setMargins(0, 0, 0, 10)
//                            card.layoutParams = layoutParams
//                            linear.addView(card)
//                        }
//                    }
//                }
//
//            }
//            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
//                Log.d("Controller", "ошибка")
//            }
//        })
//    }
