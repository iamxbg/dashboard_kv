package com.example.dashboard_kv.api

import retrofit2.http.Path


interface SuppliesApi:WebApi {

    fun info(@Path("id") id:Long)

    fun infoAdd()

    fun infoGet(@Path("code") code:String)


    fun infoList()

    fun infoNumberList()



}


