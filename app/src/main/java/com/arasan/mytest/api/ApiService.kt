package com.arasan.mytest.api

import com.arasan.mytest.helper.Constants
import com.arasan.mytest.models.ResDictionary
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constants.END_POINT+"/{word}")
    suspend fun getAllData(@Path("word") word: String):Response<ResponseBody>
}