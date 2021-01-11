package com.example.dashboard_kv.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.http.*
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

interface LoginApi : WebApi {


    @POST("login")
    fun login(@Body loginBody:LoginBody):Call<Unit>;

    @GET("sockjs-node/info")
    @Headers("Referer:http://192.168.1.11/index")
    fun info(@Query("t") t:Long):Call<Map<String,Object>>;

    @GET("dev-api/captchaImage")
    fun captchaImage():Call<Map<String,Object>>

    @GET("/")
    fun loginIndex():Call<retrofit2.Response<Any>>;

    @GET("dev-api/getInfo")
    fun getUserInfo():Call<Map<String,Any>>


}

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
 */
data class LoginBody(public val code:String,
                     public val fingerprint:String,
                     public val password:String,
                     public val username:String,
                     public val uuid:String) {

}


class LoginService() {

    companion object{

        val loginApi:LoginApi = WebUtil.getService(LoginApi::class.java)

    }


    @RequiresApi(Build.VERSION_CODES.O)
     fun login(loginBody: LoginBody) {

        loginApi.login(LoginBody("code","fingerprint",getEcryptedPassword("111111"),"admin","uuid"))

    }

     fun info(timestamp:Long): Unit {
        val loginApi = WebUtil.getService(LoginApi::class.java)
        //println(loginApi.info(timestamp).execute().message())
    }


    fun getCaptchaImage():Unit{

        //val loginApi = WebUtil.getService(LoginApi::class.java)

        //print(loginApi.captchaImage().execute().body())
    }


    val PUBLIC_KEY :String ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU7GKkferYtm3Z2JiiYUVWb52KHoanPD8" +
            "K/f4jexS0Asi52ONmiKdeT2vyRc7s78wCcvHfx02SbrGUtKDIfRzPqmp6uF7Nx8+" +
            "24FkLGI8iIJbm3HTsSBR5j3JbIU4FbYg0C7b+" +
            "8RMEJGGRtGE0X7YLnIknzR+euEP5tjVDinLxHQIDAQAB";

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEcryptedPassword(password: String):String{

         val keySpec = X509EncodedKeySpec( Base64.getDecoder().decode(PUBLIC_KEY));
         val keyFactory = KeyFactory.getInstance("RSA");
         val publicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE,publicKey)
        println("password:"+password)
      val encodedPassWd =   Base64.getEncoder().encodeToString(cipher.doFinal(password.toByteArray()))
        println("encoded-password:"+encodedPassWd)
        return encodedPassWd;
    }


}











