import com.example.dashboard_kv.api.FtpFilesApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * 工艺文件接口测试
 */
@RunWith(JUnit4::class)
class FtpFilesApiTest {


     val api = WebUtil.getService(FtpFilesApi::class.java)


    @Test
     fun testFileList(){

       val call =  api.fileList().execute()
        Assert.assertEquals(200,call.code())

    }



}