package com.technowave.ilabanktest.repository

import android.content.Context
import com.technowave.ilabanktest.network.Api
import java.io.IOException
import javax.inject.Inject

class Repo @Inject constructor(private val api: Api) {
    suspend fun getHomePage() = api.getHomePage()

/*    suspend fun getLocalHomePage(): String {
        var jsonString = ""
        try {
            val inputScream = context.assets.open("home_page.json")

            val size = inputScream.available()
            val buffer = ByteArray(size)
            inputScream.read(buffer)
            inputScream.close()

            //jsonString = String(buffer, "UTF-8")
        } catch (e: IOException) {
            e.printStackTrace();
            return e.toString()
        }

        return jsonString;
    }*/

}