import org.junit.Assert
import retrofit2.Response

object AssertUtil {

    fun <T> basicAssert(resp:Response<T>){

        Assert.assertEquals(200,resp.code())

    }


    private fun assertBasic(resp:Response<Any> ){
        Assert.assertEquals(200,resp.code())
    }


    //private fun doAssert(resp:Response<Any>)




}