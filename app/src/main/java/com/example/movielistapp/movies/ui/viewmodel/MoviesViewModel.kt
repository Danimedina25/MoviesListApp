package com.example.movielistapp.movies.ui.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.di.utils.NetworkResult
import com.example.movielistapp.movies.domain.model.MovieDataModel
import com.example.movielistapp.movies.domain.usecases.GetMoviesNowPlayingUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    val getMoviesUseCase: GetMoviesNowPlayingUseCase
):ViewModel(){

    private val _movies = MutableLiveData<List<MovieDataModel>>()
    val movies: LiveData<List<MovieDataModel>>
        get() = _movies

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getMovies(){
        _isLoading.value = true
        viewModelScope.launch {
            when(val result = getMoviesUseCase()){
                is NetworkResult.Success -> {
                    Log.d("success", Gson().toJson(result))
                    _movies.value = result.data
                    _isLoading.value = false
                }
                is NetworkResult.Error -> {
                    Log.d("error", Gson().toJson(result))
                    _error.value = result.message
                    _isLoading.value = false
                }
            }
        }
    }

    fun cambiarFormatoFecha(fechaOriginal: String): String {
        // Define el formato original y el nuevo formato deseado
        val formatoOriginal = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val nuevoFormato = SimpleDateFormat("MMMM dd ',' yyyy", Locale.getDefault())

        // Parsear la fecha original y formatearla en el nuevo formato
        val fecha = formatoOriginal.parse(fechaOriginal)
        return fecha?.let { nuevoFormato.format(it) } ?: "Fecha no válida"
    }
}