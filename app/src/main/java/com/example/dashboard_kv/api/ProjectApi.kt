package com.example.dashboard_kv.api

import androidx.annotation.IntegerRes
import org.jetbrains.annotations.Nullable
import retrofit2.Call
import retrofit2.http.*
import java.util.Date;

data class ProjectInfo(
        var actTaskStatus:String="",
        var auditFlag:Int=0,
        var auditOp:Int=0,
        var beginTime:String="",
        var businessKey:String="",
        var craftUserIds:String="",
        var craftUserNames:String="",
        var createBy:String="",
        var createTime:String="",
        var deliveryUserIds:String="",
        var deliveryUserNames:String="",
        var designFileIds:String="",
        var endTime:String="",
        var frameNo:String="",
        var frameNumber:String="",
        var id:Long=0,
        var instanceId:String="",
        var makeFileIds:String="",
        var modelNo:String="",
        var name:String="",
        var opUserIds:String="",
        var opUserNames:String="",
        var param:Any="",
        var projectLeaderUserId:String="",
        var projectLeaderUserName:String="",
        var projectManagerUserId:String="",
        var projectManagerUserName:String="",
        var projectNo:String="",
        var remark:String="",
        var searchValue:String="",
        var status:String="",
        var updateBy:String="",
        var updateTime:String="",
        var workflowProject:Any=""

){

}



interface  ProjectApi :WebApi {

    @GET("dev-api/workflow/project/list")
    fun projectList():Call<ResponseEntity<ProjectInfo>>

    @GET("dev-api/workflow/project/query/{bussinessKey}")
    fun projectQueryByBussinessKey(@Path("bussinessKey") bussinessKey:String):Call<ResponseEntity<ProjectInfo>>
}