package com.example.movielistapp.movies.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movielistapp.R
import com.example.movielistapp.databinding.ActivityMovieDetailBinding
import com.example.movielistapp.databinding.ActivityMoviesListBinding
import com.example.movielistapp.di.utils.Constants
import com.example.movielistapp.movies.domain.model.MovieDataModel
import com.example.movielistapp.movies.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        val movieData = intent.getParcelableExtra<MovieDataModel>("movieData")

        showDataMovie(movieData!!)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun showDataMovie(movieData: MovieDataModel){
        Glide.with(this)
            .load("${Constants.BASE_URL_IMAGEN}/${movieData.poster_path}")
            .into(binding.ivMovie)

        binding.txtTitulo.text = movieData.title
        binding.txtFechaEstreno.text = moviesViewModel.cambiarFormatoFecha(movieData.release_date)
        binding.txtDescripcion.text = "Description: ${movieData.overview}"

    }
}