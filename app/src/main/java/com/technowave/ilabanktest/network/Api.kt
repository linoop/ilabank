package com.technowave.ilabanktest.network

import com.technowave.ilabanktest.models.GetDataResponse
import com.technowave.ilabanktest.utils.Constants.GET_HOME_DATA
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET(GET_HOME_DATA)
    suspend fun getHomePage(): Response<GetDataResponse>
}