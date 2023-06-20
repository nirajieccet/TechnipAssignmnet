package com.example.technipassignmnet.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technipassignmnet.data.model.Results
import com.example.technipassignmnet.domain.repository.MovieRepository
import com.example.technipassignmnet.utils.Resource
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository): ViewModel() {
    var movieLiveData = MutableLiveData<Resource<List<Results>>>()
    val errorMessage = MutableLiveData<String>()

    fun getTopRatedMoviesList() {
        viewModelScope.launch {
            try {
                val movieResponse = repository.getTopRatedMoviesList()
                if (movieResponse != null) {
                    Log.e("MovieViewModel", movieResponse.results.size.toString())
                    movieLiveData.value =  Resource.success(movieResponse.results)
                } else {
                    errorMessage.value = "Failed to fetch movie details"
                }
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch movie details"
            }
        }
    }
}