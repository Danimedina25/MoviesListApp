package com.example.movielistapp.login.ui.Viewmodel

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.di.utils.NetworkResult
import com.example.movielistapp.movies.domain.model.MovieDataModel
import com.example.movielistapp.movies.domain.usecases.GetMoviesNowPlayingUseCase
import com.example.movielistapp.movies.ui.view.MoviesListActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val getMoviesUseCase: GetMoviesNowPlayingUseCase
):ViewModel(){


    private val auth = FirebaseAuth.getInstance()

    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean>
        get() = _isLogged

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLogged.value = true
                    _error.value = ""
                    _isLoading.value = false
                } else {
                    _isLogged.value = false
                    _error.value = task.exception!!.message
                    _isLoading.value = false
                }
            }
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}