import com.example.dashboard_kv.api.TaskApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.http.GET

@RunWith(JUnit4::class)
class TaskApiTest {

    val api = WebUtil.getService(TaskApi::class.java)

    @Test
    fun testTaskList(){

        val resp = api.taskList().execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }


    @Test
    fun testTaskDetail(){

        val id =7.toLong();
        val resp = api.taskDetail(id).execute()

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

}