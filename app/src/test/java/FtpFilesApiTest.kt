import com.example.dashboard_kv.api.FtpFilesApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Assert
import org.junit.Ignore
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

       val resp =  api.fileList().execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }

    @Test
    fun testGetFile(){
        val id = 83.toLong()
        val resp = api.fileDetail(id).execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Ignore
    @Test
    fun testDownload(){
        val id = 83.toLong()
        val resp = api.downloadFile(id).execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }



}