import com.example.dashboard_kv.api.LoginApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Date

@RunWith(JUnit4::class)
class WebUtilTest {



    @Test
    fun testLogin_Info(){

       val api =  WebUtil.getService(LoginApi::class.java)

        api.info(Date().time).execute()
    }


}