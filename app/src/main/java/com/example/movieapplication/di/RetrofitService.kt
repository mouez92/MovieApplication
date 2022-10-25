package com.example.movieapplication.di


import com.example.movieapplication.movie.Movie
import com.example.movieapplication.movie.Movie_result
import com.example.movieapplication.movie_detail.Movie_Detail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("/3/discover/movie")
    suspend fun getAllMovies(@Query("api_key") api_key: String) : Response<Movie_result>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movie_id : String,@Query("api_key") api_key: String) : Response<Movie_Detail>
    //suspend fun getCommercialOffers(@Path("param_books_isbns") num : String) : Response<List<Offer>>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}