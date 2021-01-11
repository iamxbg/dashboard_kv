package com.example.dashboard_kv.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationApi:WebApi {

    /**
     * "1"- 发布的消息
     */
    @GET("dev-api/msg/pub/management/publishList")
    fun getNotifications(@Query("status") status:String):Call<ResponseEntity<Notification>>


}

/**
 * 通知实体类
 */
data class  Notification(
        val content:String,         //信息内容
        val createBy:String,        //创建人
        val createTime:String,      //创建时间
        val deleteBy:String,        //删除人ID
        val deleteTime:String,      //删除时间
        val feedbackFlag:String,    //信息是否可以被反馈
        val feedbackNumber:Long,    //反馈量
        val id:Long,
        val isTop:String,   //置顶 1置顶 0默认
        val publishBy:String,   //发布人ID
        val publishTime:String, //发布时间
        val status:String,  //0:新建 1:发布 2:删除
        val title:String,   //信息标题
        val updateBy:String,    //更新者
        val updateTime:String,  //更新时间
        val visitorNumber:Long  //访问量

){

}