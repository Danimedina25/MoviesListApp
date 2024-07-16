package com.example.material_electoral.materiales.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielistapp.R
import com.example.movielistapp.databinding.ItemMovieBinding
import com.example.movielistapp.di.utils.BaseViewHolder
import com.example.movielistapp.di.utils.Constants
import com.example.movielistapp.movies.domain.model.MovieDataModel


class MoviesRvAdapter (private val context: Context, private val moviesList: List<MovieDataModel>,
                       private val itemClickListener: (MovieDataModel) -> Unit
) :
RecyclerView.Adapter<BaseViewHolder<*>>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder -> holder.bind(moviesList[position], position)
        }
    }

    inner class MainViewHolder(itemView: View): BaseViewHolder<MovieDataModel>(itemView){
        val binding = ItemMovieBinding.bind(itemView)

        override fun bind(movie: MovieDataModel, position: Int) {
            binding.txtNombre.text = movie.title
            binding.txtCalificacion.text = "Calificación: ${movie.vote_average}"
            Glide.with(binding.ivMovie.context)
                .load("${Constants.BASE_URL_IMAGEN}/${movie.poster_path}")
                .into(binding.ivMovie)
            binding.cvMovie.setOnClickListener{
                itemClickListener(movie)
            }
        }
    }

    fun getListOfMovies(): List<MovieDataModel> {
        return moviesList
    }
}