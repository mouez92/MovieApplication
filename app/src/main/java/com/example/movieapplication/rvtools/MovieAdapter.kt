package com.example.movieapplication.rvtools
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.di.Constants
import com.example.movieapplication.movie.Movie
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter (
    private val moviesList:List<Movie>,
    private val clickListener:(Movie)->Unit
) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.adapter_movie,parent,false)
            return MyViewHolder(listItem)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val fruit = moviesList[position]
            holder.bind(fruit,clickListener)
        }

        override fun getItemCount(): Int {
            return moviesList.size
        }



    class MyViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bind(movie: Movie, clickListener:(Movie)->Unit) {
            val tvtitle = view.findViewById<TextView>(R.id.tvtitle)
            val tvyear = view.findViewById<TextView>(R.id.tv_year)
            val postImageView: ImageView = view.findViewById(R.id.imageview)

            tvtitle.text = movie.title
            tvyear.text = movie.release_date
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = format.parse(movie.release_date)
            val year = DateFormat.format("yyyy", date) as String
            tvyear.text = year
            Glide.with(postImageView.context).load(Constants.BASE_URL+"3/"+movie.poster_path ).into(postImageView)

            view.setOnClickListener {
                clickListener(movie)
            }
        }
    }


}