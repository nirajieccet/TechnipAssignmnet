package com.example.technipassignmnet.domain.repository

import android.util.Log
import com.example.technipassignmnet.data.network.RetrofitService
import com.example.technipassignmnet.data.model.MovieResponse
import com.example.technipassignmnet.utils.Constants

open class MovieRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getTopRatedMoviesList(): MovieResponse? {
        val response = retrofitService.getTopRatedMovies(Constants.API_KEY)
        Log.e("MovieRepository", response.isSuccessful.toString())
        if (response.isSuccessful) {
            return response.body()?.let { movieResponse ->
                movieResponse
            }
        }
        return null
    }
}