package com.example.movieapplication.di

import com.example.movieapplication.di.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies(api_key: String) = retrofitService.getAllMovies(api_key)

    suspend fun getMovieDetails(api_key: String, movie_id: String) = retrofitService.getMovieDetails(movie_id,api_key)

}