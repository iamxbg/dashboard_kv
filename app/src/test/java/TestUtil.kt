import com.example.dashboard_kv.api.ResponseEntity
import org.junit.Assert
import retrofit2.Response
import java.net.ResponseCache

object TestPrinter {

    fun printRespBasicInfo(resp:Response<*>){
        println(resp.code())
        Assert.assertEquals(200,resp.code())
        println(resp.message())
        Assert.assertEquals("OK",resp.message())
    }

    fun <T> printResponseEntity(resp:Response<T>){
        val re = resp.body() as ResponseEntity<*>
        println("re-code:"+re.code)
        println("re-message:"+re.msg)
        if(re.rows!=null) println("row-size:"+re.rows.size)
        if(re.data!=null)  println("data:"+re.data)
    }
}