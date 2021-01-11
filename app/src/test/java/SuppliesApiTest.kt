import com.example.dashboard_kv.api.SuppliesApi
import com.example.dashboard_kv.api.WebUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.util.FragmentTestUtil
import retrofit2.http.GET
import retrofit2.http.Path

@RunWith(JUnit4::class)
class SuppliesApiTest {

    val api = WebUtil.getService(SuppliesApi::class.java)

    @Test
    fun getInfo(){
        val id = 153.toLong()
        val resp = api.info(id).execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)
    }

    @Test
    fun getInfoByCode(){

    }

    @Test
    fun getInfoList(){
        val call = api.infoList()
        val resp = call.execute();

        TestPrinter.printRespBasicInfo(resp)
        TestPrinter.printResponseEntity(resp)

    }


}