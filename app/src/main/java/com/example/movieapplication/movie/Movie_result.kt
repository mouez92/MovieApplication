package com.example.movieapplication.movie

import com.google.gson.annotations.SerializedName

data class Movie_result(
    val page : Int,

    @SerializedName("results")
    val movies: List<Movie>,
)
