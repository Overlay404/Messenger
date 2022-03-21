package com.example.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EchoController {

    @GET("/addGroups")
    fun addGroups(@Query("usersCodeList") usersCodeList: ArrayList<String>, @Query("name") name: String):Call<Boolean>

    @GET("/showGroups")
    fun showGroups(@Query("code")code: String):Call<List<Groups>>

    @GET("/addUser")
    fun addUser(@Query("code")code: String, @Query("name")name: String):Call<Boolean>

    @GET("/showUser")
    fun showUser(@Query("code")code: String):Call<List<UserName>>

    @GET("/showAllUser")
    fun showAllUser():Call<List<UserName>>

    @GET("/addMessage")
    fun addMessage(@Query("text")text: String,@Query("idGroups")idGroups: Int, @Query("nameUser")nameUser: String): Call<Boolean>

    @GET("/showMessage")
    fun showMessage(@Query("idGroups") idGroups: Int): Call<List<Message>>

    @GET("/verification")
    fun verification(@Query("codeUser")codeUser: String): Call<Boolean>

}