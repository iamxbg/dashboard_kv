package com.example.dashboard_kv.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dashboard_kv.fragment.PUBLIC_KEY
import com.sun.crypto.provider.RSACipher
import com.sun.crypto.provider.SunJCE
import java.security.KeyFactory
import java.security.Security
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object EncryptUtil {


    fun getEcryptedPassword(password: String):String{



        Security.insertProviderAt(SunJCE(),1);

        Security.getProviders()
            .forEach {
                println("name: ${it.name} info: ${it.info}")
            }



       // val ch = RSACipher()


        val keySpec = X509EncodedKeySpec( Base64.getDecoder().decode(PUBLIC_KEY));

        val keyFactory = KeyFactory.getInstance("RSA");
        //val keyFactory = KeyFactory.getInstance("RSA","SunJSSE")


        val publicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE,publicKey)
        println("password:"+password)

        println("chipher: => algorithm: ${cipher.algorithm?:""} , blockSize:${ cipher.blockSize?:""} " +
                "exemptionMechanism.name: ${ cipher.exemptionMechanism?.name?:""} " +
                "exemptionMechanism.provider.name: ${ cipher.exemptionMechanism?.provider?.name?:""} " +
                "exemptionMechanism.provider.info ${ cipher.exemptionMechanism?.provider?.info?:""} " +
                "provider.name ${  cipher.provider?.name?:""} " +
                "provider.info ${cipher.provider?.info?:"" }")











        val encodedPassWd =   java.util.Base64.getEncoder().encodeToString(cipher.doFinal(password.toByteArray()))
        println("encoded-password:"+encodedPassWd)
        return encodedPassWd;
    }


    fun getEcryptedPasswordAndroid(password: String):String{

        val encryptedKey = android.util.Base64.decode(PUBLIC_KEY,android.util.Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec( encryptedKey);
        val keyFactory = KeyFactory.getInstance("RSA");
        val publicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA/None/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE,publicKey)
        println("password:"+password)
        val encodedPassWd =   android.util.Base64.encodeToString(cipher.doFinal(password.toByteArray()), android.util.Base64.NO_WRAP)
        return encodedPassWd
    }
}