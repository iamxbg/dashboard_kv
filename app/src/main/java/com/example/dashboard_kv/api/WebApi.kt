package com.example.dashboard_kv.api

interface  WebApi {

}


class ResponseEntity<T:Any>(val total:Int,val rows:List<T>,val data:T,val code:Int,val msg:String) { }