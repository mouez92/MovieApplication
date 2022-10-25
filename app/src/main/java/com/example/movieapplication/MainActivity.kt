package com.example.movieapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.movieapplication.databinding.ActivityMainBinding
import com.example.movieapplication.di.MainRepository
import com.example.movieapplication.di.RetrofitService
import com.example.movieapplication.movie.Movie
import com.example.movieapplication.movie.MovieViewModel
import com.example.movieapplication.movie.MovieViewModelFactory
import com.example.movieapplication.rvtools.MovieAdapter

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel
    lateinit var binding: ActivityMainBinding
    private val adapter = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MovieViewModelFactory(mainRepository)).get(MovieViewModel::class.java)


        viewModel.movieList.observe(this){
            val adapter = MovieAdapter(
                it,
                {
                        selectedItem: Movie -> listItemClicked(selectedItem)
                }
            )
            binding.recyclerview.adapter = adapter
            adapter.notifyDataSetChanged()

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

        viewModel.getAllMovies()

    }

    private fun listItemClicked(movie: Movie){
        Toast.makeText(
            this@MainActivity,
            "Movie is : ${movie.title}",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", movie.id)

        startActivity(intent)
    }
}