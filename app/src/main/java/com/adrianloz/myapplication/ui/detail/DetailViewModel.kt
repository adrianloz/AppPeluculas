package com.adrianloz.myapplication.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianloz.myapplication.db.DataBaseServise
import com.adrianloz.myapplication.models.Results
import com.adrianloz.myapplication.models.Video
import com.adrianloz.myapplication.network.ApiService
import com.adrianloz.myapplication.utils.Helper
import com.adrianloz.myapplication.utils.Keys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: ApiService,
    private val helper: Helper,
    private val databaseService: DataBaseServise
) : ViewModel(){

    val videoMutaleLiveData = MutableLiveData<List<Results>>()
    val msg = MutableLiveData<String>()
    val loading=MutableLiveData<Boolean>()

    init {
        loading.postValue(false)
}
    fun getData(idMovie: Long){
        viewModelScope.launch {
            try {
                coroutineScope {
                    delay(1000)
                    loading.postValue(true)
                    try {
                        val resultDao = databaseService.resultsDao()
                        val video = async {
                            val videoResult = apiService.getVideo(idMovie)
                            for (i : Results in videoResult?.results!!){
                                i.typeVideo = videoResult.id.toString()
                                i.pKey = videoResult.id.toString()+""+i.id
                            }
                            resultDao.insertManyVideo(videoResult.results!!)
                        }

                        video.await().let {
                            Log.i("awaited",it.toString())
                            videoMutaleLiveData.postValue(
                                withContext(Dispatchers.Default){
                                    resultDao.getResultsByidVideo(idMovie.toString())
                                }
                            )
                        }


                    }catch (exception: Exception) {
                        Log.i("popular exeception", exception.message.toString())
                    }
                    loading.postValue(false)
                }
            }
            catch (e: Exception){
                Log.i("popular exeception", e.message.toString())
            }
            catch (unknownHostException: UnknownHostException) {
                Log.i("error",unknownHostException.message.toString())
                msg.postValue("No internet connection")
            }
        }
    }
}