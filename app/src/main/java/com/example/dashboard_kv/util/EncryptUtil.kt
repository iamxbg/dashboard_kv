package com.example.dashboard_kv.util

//import com.sun.crypto.provider.SunJCE
import com.example.dashboard_kv.api.PUBLIC_KEY
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.KeyFactory
import java.security.Security
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object EncryptUtil {





    fun getEcryptedPasswordAndroid(password: String):String{

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