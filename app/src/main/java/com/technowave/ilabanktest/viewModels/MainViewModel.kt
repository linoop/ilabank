package com.technowave.ilabanktest.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.technowave.ilabanktest.repository.Repo
import com.technowave.marksandspencer.utils.DataResource
import com.technowave.msdccyclecount.utils.CheckConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(app: Application, private val repo: Repo) :
    AndroidViewModel(app) {

    //Observe home page data
    fun getHomePage() = liveData {
        emit(DataResource.Loading())
        try {
            if (CheckConnectivity.hasInternetConnection(getApplication())) {
                val response = repo.getHomePage()
                if (response.isSuccessful) {
                    emit(DataResource.Success(response.body()))
                } else {
                    emit(DataResource.Error(response.message()))
                }
            } else {
                emit(DataResource.Error("No Internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> emit(DataResource.Error("Network failure"))
                else -> emit(DataResource.Error("Conversion error"))
            }
        }
    }
}

