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



    @Test
    fun testListPageSize5(){

        val resp = projectApi.projectList(1,5).execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Test
    fun testQueryByBussinessKey(){
        val bk = "projectApprovalWorkflow.1"
        val resp = projectApi.projectDetailQueryByBussinessKey(bk).execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }


    @Test
    fun testQueryProjectDetailById(){
        val id = 1L;

        val resp = projectApi.projectDetailQueryById(id).execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

}