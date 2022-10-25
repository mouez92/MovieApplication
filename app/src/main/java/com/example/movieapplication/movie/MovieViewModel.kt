package com.example.movieapplication.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.di.Constants
import com.example.movieapplication.di.MainRepository

import kotlinx.coroutines.*

class MovieViewModel  constructor(private val mainRepository: MainRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()


    fun getAllMovies() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllMovies(Constants.api_key)
            withContext(Dispatchers.Main) {
                //Log.e("MovieViewModel"," response " + response.toString())
                //Log.e("MovieViewModel"," body " + response.body())

                if (response.isSuccessful) {
                    //Log.e("MovieViewModel","isSuccessful response " + response.toString())

                    movieList.postValue(response.body()?.movies)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
    private fun onError(message: String) {
        //Log.e("MovieViewModel"," onError " + message)
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}