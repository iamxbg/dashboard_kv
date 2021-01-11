package api

import com.example.dashboard_kv.api.NotificationApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NotificationApiTest {

    val api = WebUtil.getService(NotificationApi::class.java)

    @Test
    fun testGetNotificationOfPublished(){

        val resp = api.getNotifications("1").execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }

}