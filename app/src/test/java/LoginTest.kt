import android.util.Log
import com.example.dashboard_kv.api.LoginApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Assert
import org.junit.BeforeClass
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

        val call =loginApi.loginIndex()

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

        println(resp.message())

        val map = resp.body();

        Assert.assertNotNull(map)

        for(k in map!!.keys){
            println("key:"+k+":val"+map.get(k))
        }

    }

    @Test
    fun testCaptcha():Unit {

        val resp = loginApi.captchaImage().execute()

        println(resp.message())

        val body = resp.body()!!

        for(k in body.keys)
            println("key:"+k+":val"+body.get(k))
    }


}