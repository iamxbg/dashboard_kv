package com.example.dashboard_kv.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET




interface SystemStorageApi:WebApi {

    @GET("system/storage/files/list")
    fun fileList():Call<ResponseEntity<FtpFile>>

}


data class FtpFile(
        val aliasName:String,
        val auditOp:Integer,
        val beginTime:String,
        val businessKey:String,
        val createBy:String,
        val createTime:String,
        val endTime:String,
        val fileSize:Long,
        val fileSuffix:String,
        val ftpPath:String,
        val fullFilePath:String,
        val id:Long,
        val level:String,
        val localPdfPath:String,
        val localResourcePath:String,
        val name:String,
        val params:Any?,
        val parentFtpPath:String,
        val parentId:Long,
        val remark:String,
        val searchValue:String,
        val sysStorageFiles:List<Any>?,
        val type:String,
        val updateBy:String,
        val updateTime:String
){}