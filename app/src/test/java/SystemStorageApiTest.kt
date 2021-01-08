import com.example.dashboard_kv.api.SystemStorageApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SystemStorageApiTest {


     val api = WebUtil.getService(SystemStorageApi::class.java)


    @Test
     fun testFileList(){
           val call =  api.fileList().execute()

        Assert.assertEquals(200,call.code())


    }

}