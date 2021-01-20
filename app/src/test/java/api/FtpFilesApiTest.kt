package api

import TestPrinter
import com.example.dashboard_kv.api.FtpFile
import com.example.dashboard_kv.api.FtpFilesApi
import com.example.dashboard_kv.api.ResponseEntity
import com.example.dashboard_kv.api.WebUtil
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    fun testFileListAsync(){

        api.fileList()
                .enqueue(object: Callback<ResponseEntity<FtpFile>> {
                    override fun onResponse(call: Call<ResponseEntity<FtpFile>>, response: Response<ResponseEntity<FtpFile>>) {

                        TestPrinter.printRespBasicInfo(response)
                        TestPrinter.printResponseEntity(response)
                    }

                    override fun onFailure(call: Call<ResponseEntity<FtpFile>>, t: Throwable) {

                    }

                })

        Thread.sleep(5000)

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