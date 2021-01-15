package api

import android.util.Log
import com.example.dashboard_kv.api.LoginApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

@RunWith(JUnit4::class)
class LoginTest {

    companion object {

        val loginApi = WebUtil.getService(LoginApi::class.java)

    }



    @Test
    fun loginRoot(){

        val call = loginApi.loginIndex()

        //TestPrinter.printRespBasicInfo(resp)
        //TestPrinter.printResponseEntity(resp)

        call.enqueue(object : Callback<Response<Any>> {

            override fun onFailure(call: Call<Response<Any>>, t: Throwable) {
                Log.e("onFailure",t.message)
            }

            override fun onResponse(call: Call<Response<Any>>, response: Response<Response<Any>>) {
                Log.w("onResponse",response.message())
                Assert.assertNotNull(response.message())
            }

        })
    }

    @Test
    fun testInfo():Unit{
        val resp = loginApi.info(Date().time)
                .execute();

        TestPrinter.printRespBasicInfo(resp)

        val map = resp.body();
        Assert.assertNotNull(map)

        for(k in map!!.keys){
            println("key:"+k+":val"+map.get(k))
        }

    }


    @Test
    fun testDashboardTempalte(){

        val resp = loginApi.getDashBoardTempaltes().execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Test
    fun testCaptcha():Unit {

        val resp = loginApi.captchaImage().execute()

        println(resp.body()!!.msg)
        println(resp.body()!!.img)


    }

    @Test
    fun testGetUserInfo(){

        val resp = loginApi.getUserInfo().execute()

        TestPrinter.printRespBasicInfo(resp)

        val map = resp.body() as Map<String,Any>

        Assert.assertNotNull(map.get("user"))
        Assert.assertNotNull(map.get("roles"))
        Assert.assertNotNull(map.get("permissions"))


    }



}