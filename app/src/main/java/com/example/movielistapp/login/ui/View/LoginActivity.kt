package com.example.movielistapp.login.ui.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movielistapp.databinding.ActivityLoginBinding
import com.example.movielistapp.login.ui.Viewmodel.LoginViewModel
import com.example.movielistapp.movies.ui.view.MoviesListActivity
import com.example.movielistapp.movies.ui.viewmodel.MoviesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        validarSesionUsuario()

        binding.btnLogin.setOnClickListener{
            val email = binding.etUsuario.text.toString()
            val password = binding.etPassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Ingresa un email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!loginViewModel.isValidEmail(email)){
                Toast.makeText(this, "Ingresa un email válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        setupObservers()
    }

    private fun setupObservers(){
        loginViewModel.error.observe(this, Observer {
            Log.d("result", Gson().toJson(it))
            binding.btnLogin.isEnabled = true
            Toast.makeText(this,"Authentication failed: ${it.toString()}", Toast.LENGTH_SHORT).show()
        })

        loginViewModel.isLoading.observe(this, Observer {
            if (it){
                binding.btnLogin.isEnabled = false
                binding.loader.visibility = View.VISIBLE
            }
            else{
                binding.btnLogin.isEnabled = true
                binding.loader.visibility = View.GONE
             }

        })

        loginViewModel.isLogged.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MoviesListActivity::class.java))
                finish()
            }
        })
    }

    private fun loginUser(email: String, password: String) {
        loginViewModel.login(email, password)
    }

    private fun validarSesionUsuario(){
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MoviesListActivity::class.java))
            finish()
        }
    }

}