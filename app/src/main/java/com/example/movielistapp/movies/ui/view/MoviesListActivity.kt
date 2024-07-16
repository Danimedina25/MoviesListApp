package com.example.movielistapp.movies.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.material_electoral.materiales.ui.MoviesRvAdapter
import com.example.movielistapp.R
import com.example.movielistapp.databinding.ActivityMoviesListBinding
import com.example.movielistapp.login.ui.View.LoginActivity
import com.example.movielistapp.movies.ui.viewmodel.MoviesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListBinding
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: MoviesRvAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        moviesViewModel.getMovies()

        binding.rvList.layoutManager = LinearLayoutManager(this)

        setupObservers()

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupObservers(){
        moviesViewModel.movies.observe(this, Observer {
            Log.d("result", Gson().toJson(it))
            adapter = MoviesRvAdapter(this, it!!){ movie ->
                val intent = Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra("movieData", movie)
                }
                startActivity(intent)
            }
            binding.rvList.adapter = adapter
        })
        moviesViewModel.error.observe(this, Observer {
            Log.d("result", Gson().toJson(it))
            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
        })

        moviesViewModel.isLoading.observe(this, Observer {
            if (it)
                binding.loader.visibility = View.VISIBLE
            else
                binding.loader.visibility = View.GONE
        })
    }

}