package com.example.movieapplication.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapplication.di.MainRepository

class MovieDetailViewModelFactory constructor(
    private val repository: MainRepository,
    private val id_movie: String

    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            MovieDetailViewModel(this.repository,id_movie) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}