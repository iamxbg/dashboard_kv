package com.example.dashboard_kv.api

import retrofit2.Call
import retrofit2.http.*

interface LoginApi : WebApi {


    @POST("dev-api/login")
    fun login(@Body loginReq:LoginReq):Call<LoginResp>;

    @GET("sockjs-node/info")
    @Headers("Referer:http://192.168.1.11/index")
    fun info(@Query("t") t:Long):Call<Map<String,Object>>;

    @GET("dev-api/captchaImage")
    fun captchaImage():Call<CaptchaImage>

    @GET("/")
    fun loginIndex():Call<retrofit2.Response<Any>>;

    @GET("dev-api/getInfo")
    fun getUserInfo():Call<Map<String,Any>>


    @GET("dev-api/board/login/getPageInfo")
    fun getDashBoardTempaltes():Call<ResponseEntity<DashboardTemplate>>


}

/**
 * 看板模板
 */
data class DashboardTemplate(val createBy:String,val createTime:String,val remark:String?,val templateId:Int,val templateName:String,val pageInfoList:List<TemplatePage>){}

/**
 * 看板模板-分页
 */
data class TemplatePage(val pageId:Int,val elementLocationList:List<TemplateWindow>){}

/**
 * 看板模板-分页-窗口
 */
data class TemplateWindow(val pageLocationId:Int,val elementId:Int){}

/**
 * 用户的基本信息类
 */
data class UserModel(
        val admin:Boolean=false,
        val avatar:String,
        val createBy:String,
        val createTime:String,
        val delFlag:String?,
        val deptId:Int?,
        val email:String?,
        val loginDate:String?,
        val loginIp:String?,
        val nickName:String?,
        val phonenumber:String?,
        val remark:String?,
        val sex:String?,
        val status:String,
        val userId:Long,
        val userName:String,
        val roles:List<RoleModel>?  //角色
) {}

/**
 * 角色数据类
 */
data class RoleModel(
        val admin:Boolean,
        val dataScope:String,
        val deptCheckStrictly:Boolean = false,
        val flag:Boolean=false,
        val menuCheckStrictly:Boolean = false,
        val roleId:Int,
        val roleKey:String,
        val roleName:String,
        val roleSort:String,
        val status:String
){}


/**
 * 请求实体类
 *  code 验证码
 *  uuid - captchaImage 返回的uuid ;必须与code 匹配！
 */
data class LoginReq(public val code:String,
                    public val password:String,
                    public val username:String,
                    public val uuid:String,
                    public val loginOs:String ="ANDROID")

/**
 * 请求返回值
 */
data class LoginResp(val code:Int,val msg:String,val token:String)

/**
 * captcha image
 */
data class CaptchaImage(val code:Int,val img:String,val msg:String,val uuid:String){}











