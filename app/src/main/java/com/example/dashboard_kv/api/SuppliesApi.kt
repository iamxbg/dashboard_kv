package com.example.dashboard_kv.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  物料列表
 */
interface SuppliesApi:WebApi {

    /**
     * 获取物料明细详细信息
     */
    @GET("dev-api/supplies/info/{id}")
    fun info(@Path("id") id:Long):Call<ResponseEntity<SupplyStaff>>

    /**
     *
        根据二维码获取物料信息
     */
    @GET("dev-api/supplies/info/get/{code}")
    fun infoGet(@Path("code") code:String):Call<ResponseEntity<SupplyStaff>>

    /**
        查询物料列表数量信息
     */
    @GET("dev-api/supplies/info/list")
    fun infoList():Call<ResponseEntity<SupplyStaff>>

    @GET("dev-api/supplies/info/list")
    fun infoList(@Query("exportStatus") exportStatusStr:List<String>):Call<ResponseEntity<SupplyStaff>>

}

/**
 *  物料实体类
 */
data class SupplyStaff(
        var auditOp:Int=0,              //审核的操作 0不通过 1通过 其他条件见具体业务
        var batchNumber:String,         //批次/件号
        var beginTime:String?,          //开始时间
        var checkNumber:String?,        //检验字号
        var code:String?=null,          //字符串
        var createBy:String,            //创建人登录用户名
        var createTime:String,          //创建时间
        var endTime:String?,            //结束时间
        var exportCause:String?,        //出库原因 多个,隔开
        var exportCauseId:String?,      //出库原因id集合 多个，隔开
        var exportCommendNum:String?,   //出库指令号
        var exportStatus:String,        //出库状态 0:已入库,未发起出库流程 1：未出库 2：配送中 3:已签收
        var gradingNumber:String?,      //段位
        var id:Long,
        var params:Any,              //请求参数
        var remark:String?,         //审批意见，备注等
        var searchValue:String?,    //查询条件
        var specification:String,   //型号/规格
        var suppliesBillExportId:Long?,  //出库表id
        var suppliesBillImportId:Long?,  //入库表id
        var suppliesCode:String,    //物料编码
        var suppliesInfo:Any?,      //查询物料明细列表
        var suppliesName:String,    //物料名称
        var suppliesNumber:String,  //数量
        var suppliesUnit:String,    //单位
        var type:String,            //类型
        var updateBy:String?,       //修改人登录用户名
        var updateTime:String?,     //修改时间
        var validityDay:Long?       //有效期 单位天
        ) {


}


