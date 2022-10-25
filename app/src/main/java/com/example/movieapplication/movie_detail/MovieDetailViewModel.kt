package com.example.movieapplication.movie_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.di.Constants
import com.example.movieapplication.di.MainRepository
import kotlinx.coroutines.*

class MovieDetailViewModel constructor(
    private val mainRepository: MainRepository,
    private val id_movie: String
    ) : ViewModel()  {
    val errorMessage = MutableLiveData<String>()
    val movieDetail = MutableLiveData<Movie_Detail>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getMovieDetail() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getMovieDetails(Constants.api_key,id_movie)
            withContext(Dispatchers.Main) {
                //Log.e("MovieDetailViewModel"," response " + response.toString())
                //Log.e("MovieDetailViewModel"," body " + response.body())

                if (response.isSuccessful) {
                    movieDetail.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response} ")
                }
            }
        }

    }
    private fun onError(message: String) {
        //errorMessage.value = message
        //loading.value = false
        //Log.e("MovieDetailViewModel"," onError " + message)

        errorMessage.postValue(message)
        loading.postValue(false)

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}