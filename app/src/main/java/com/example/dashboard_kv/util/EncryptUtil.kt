package com.example.dashboard_kv.util

import com.example.dashboard_kv.fragment.PUBLIC_KEY
//import com.sun.crypto.provider.SunJCE
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyFactory
import java.security.Security
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object EncryptUtil {

    fun testPrint(){


        Security.addProvider(BouncyCastleProvider());
        Security.getProviders()
                .forEach {
                    println("provider-Classname:${it.javaClass.canonicalName}")
                    println("name: ${it.name} info: ${it.info} ")
                    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
//                    it.services.forEach{
//                        println("type:${it.type} algorithm:${it.algorithm} classname:${it.className}")
//
//                    }
                }
    }

    fun getEcryptedPassword(password: String):String{



        //Security.insertProviderAt(SunJCE(), 1);
        Security.addProvider(BouncyCastleProvider());
        Security.getProviders()
                .forEach {
                    println("provider-Classname:${it.javaClass.canonicalName}")
                    println("name: ${it.name} info: ${it.info} ")
                    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
//                    it.services.forEach{
//                        println("type:${it.type} algorithm:${it.algorithm} classname:${it.className}")
//
//                    }
                }


        val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY));

        val keyFactory = KeyFactory.getInstance("RSA","BC");
        //val keyFactory = KeyFactory.getInstance("RSA","SunJSSE")


        val publicKey = keyFactory.generatePublic(keySpec)

        val cipher = Cipher.getInstance("RSA","BC")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

//        println("chipher: => algorithm: ${cipher.algorithm?:""} , blockSize:${ cipher.blockSize?:""} " +
//                "exemptionMechanism.name: ${ cipher.exemptionMechanism?.name?:""} " +
//                "exemptionMechanism.provider.name: ${ cipher.exemptionMechanism?.provider?.name?:""} " +
//                "exemptionMechanism.provider.info ${ cipher.exemptionMechanism?.provider?.info?:""} " +
//                "provider.name ${  cipher.provider?.name?:""} " +
//                "provider.info ${cipher.provider?.info?:"" }")


        val encodedPassWd =   java.util.Base64.getEncoder().encodeToString(cipher.doFinal(password.toByteArray()))
        return encodedPassWd;
    }


    fun getEcryptedPasswordAndroid(password: String):String{

//       Security.insertProviderAt(SunJCE(),1);
//
//        Security.getProviders()
//                .forEach {
//                    println("name: ${it.name} info: ${it.info}")
//                }

//        Security.removeProvider("AndroidNSSP")
//        Security.removeProvider("AndroidOpenSSL")
//        Security.removeProvider("CertPathProvider")
//        Security.removeProvider("AndroidKeyStoreBCWorkaround")
//        Security.removeProvider("BC")
//        Security.removeProvider("HarmonyJSSE")
//        Security.removeProvider("AndroidKeyStore")

        Security.getProviders()
                .forEach {
                    println("###################################################")
                    println("provider-Classname:${it.javaClass.canonicalName}")
                    println("name: ${it.name} info: ${it.info} ")
                    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
                    it.services.forEach{
                        println("type:${it.type} algorithm:${it.algorithm} classname:${it.className}")

                    }
                }

        val encryptedKey = android.util.Base64.decode(PUBLIC_KEY, android.util.Base64.NO_WRAP)
        val keySpec = X509EncodedKeySpec(encryptedKey);
        val keyFactory = KeyFactory.getInstance("RSA", "BC");
        val publicKey = keyFactory.generatePublic(keySpec)
        val cipher = Cipher.getInstance("RSA", "BC")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encodedPassWd =   android.util.Base64.encodeToString(cipher.doFinal(password.toByteArray()), android.util.Base64.NO_WRAP)
        return encodedPassWd
    }
}