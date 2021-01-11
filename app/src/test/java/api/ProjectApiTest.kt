package api

import com.example.dashboard_kv.api.ProjectApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import TestPrinter

@RunWith(JUnit4::class)
class ProjectApiTest {

    val projectApi = WebUtil.getService(ProjectApi::class.java)

    @Test
    fun testList(){

        val resp  = projectApi.projectList().execute()

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