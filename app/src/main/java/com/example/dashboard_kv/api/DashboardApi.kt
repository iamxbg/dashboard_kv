package com.example.dashboard_kv.api

import retrofit2.http.GET

interface  DashboardApi:WebApi {

    @GET("/board/template/list")
    fun tempalteList():Unit

}