package com.example.dashboard_kv.api

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StorageApi {

    @GET("dev-api/system/storage/files/lis")
    fun fileList(@Query("name") name:String, @Query("parentId") id:Long):Call<Gson>

}


class StorageService {

    companion object {

        val storageApi:StorageApi = WebUtil.getService(StorageApi::class.java)

    }


    fun getFileList():Unit{
        storageApi.fileList("",1).execute().message()
    }

}

