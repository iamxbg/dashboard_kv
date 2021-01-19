package api

import android.os.Parcel
import android.os.Parcelable
import com.example.dashboard_kv.util.EncryptUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EncryptTest()  {


    @Test
    fun testEncrypt(){

        EncryptUtil.testPrint()

    }

    @Test
    fun testEncryptPasswd(){

        println(EncryptUtil.getEcryptedPassword("111111"))

    }



}