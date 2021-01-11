package com.example.dashboard_kv.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 任务API
 */
interface TaskApi :WebApi {

    @GET("dev-api/workflow/project/task/list")
    fun taskList(): Call<ResponseEntity<TaskModel>>


    @GET("dev-api/workflow/project/task/{id}")
    fun taskDetail(@Path("id") id:Long):Call<ResponseEntity<TaskModel>>

}

/**
 * 任务实体类
 */
data class TaskModel(
        val assembleUserBy:String,  //配送员id
        val auditOp:Int,    //审核的操作 0不通过 1通过 其他条件见具体业务
        val beginTime:String,   //开始时间
        val businessKey:String, //业务key
        val createBy:String,    //创建人登录用户名
        val createTime:String,  //创建时间
        val deliveryUserBy:String,  //装配员id
        val endTime:String, //结束时间
        val id:String,
        val instanceId:String,  //流程实例id
        val name:String,    //项目名称
        val params:Any, //请求参数
        val projectNo:String,   //项目编号
        val projectTask:Any,    //查询项目任务列表
        val projectTaskNo:String,   //任务编号
        val qaUserBy:String,    //质检员id
        val remark:String,  //审批意见，备注等
        val searchValue:String, //查询条件
        val status:String,  //任务状态 1：新建任务 2：装配员确认 3：配送员配送零件 4装配员确认零件 5装配员组装产品 6质检员检验产品
        val taskType:Long,  //任务类型 1：任务 2：维护任务
        val updateBy:String,    //修改人登录用户名
        val updateTime:String,  //修改时间
        val workflowProjectId:String    //项
) {}