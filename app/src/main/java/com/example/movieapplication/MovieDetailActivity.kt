package com.example.movieapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapplication.databinding.ActivityMainBinding
import com.example.movieapplication.databinding.ActivityMovieDetailBinding
import com.example.movieapplication.di.Constants
import com.example.movieapplication.di.MainRepository
import com.example.movieapplication.di.RetrofitService
import com.example.movieapplication.movie_detail.MovieDetailViewModel
import com.example.movieapplication.movie_detail.MovieDetailViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_movie_detail)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        val movie_id:Int = intent.getIntExtra("movie_id",0)
        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory(mainRepository,""+movie_id)).get(MovieDetailViewModel::class.java)

        viewModel.movieDetail.observe(this){
            //Log.e("MovieDetailActivity","movieDetail size = "+it.toString())
            //Log.e("MovieDetailActivity","movieDetail  = "+it)
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = format.parse(it.release_date)
            val year = DateFormat.format("yyyy", date) as String

            Glide.with(this).load(Constants.BASE_URL+"3/"+it.poster_path ).into(binding.imageview)
            binding.tvYear.text =year
            binding.tvtitle.text =it.title
            binding.tvDetails.text=it.overview
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })
        viewModel.getMovieDetail()

    }
}