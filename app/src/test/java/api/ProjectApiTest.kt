package api

import com.example.dashboard_kv.api.ProjectApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import TestPrinter
import com.example.dashboard_kv.api.ProjectInfo
import com.example.dashboard_kv.api.ResponseEntity
import org.junit.Ignore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(JUnit4::class)
class ProjectApiTest {

    val projectApi = WebUtil.getService(ProjectApi::class.java)

    @Test
    fun testListAll(){

        val resp  = projectApi.projectList().execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }

    @Test
    fun testListApproved(){

        val resp = projectApi.projectListApproved().execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Ignore
    @Test
    fun tsetListAllAsync(){

        projectApi.projectList().enqueue(
                object : Callback<ResponseEntity<ProjectInfo>> {
                    override fun onResponse(call: Call<ResponseEntity<ProjectInfo>>, response: Response<ResponseEntity<ProjectInfo>>) {

                        TestPrinter.printRespBasicInfo(response)
                        TestPrinter.printRespBasicInfo(response)
                    }

                    override fun onFailure(call: Call<ResponseEntity<ProjectInfo>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                }
        )

        Thread.sleep(5000)

    }

    @Test
    fun testListPageSize5(){

        val resp = projectApi.projectList(1,5).execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Test
    fun testQueryByBussinessKey(){
        val bk = "projectApprovalWorkflow.1"
        val resp = projectApi.projectQueryByBussinessKey(bk).execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }

}