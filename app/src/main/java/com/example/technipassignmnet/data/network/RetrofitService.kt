package com.example.technipassignmnet.data.network

import android.util.Log
import com.example.technipassignmnet.data.model.MovieResponse
import com.example.technipassignmnet.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.Exception

interface RetrofitService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    companion object {
        private var retrofitService: RetrofitService? = null
        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {
            try {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            }catch (e:Exception) {
                Log.d("RetrofitService", e.message.toString())
            }
            return retrofitService!!
        }
    }
}