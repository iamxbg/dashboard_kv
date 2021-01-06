package com.example.dashboard_kv.test
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.text.*
import androidx.navigation.NavArgs
import com.example.dashboard_kv.api.LoginService
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun main(){
    println("main runs")


    val loginService = LoginService();
    //println(loginService.getEcryptedPassword())


    loginService.info(Date().time)

    loginService.getCaptchaImage()
}

