package com.example.technipassignmnet.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.technipassignmnet.presentation.viewmodel.MyViewModelFactory
import com.example.technipassignmnet.presentation.adapter.MovieAdapter
import com.example.technipassignmnet.databinding.ActivityMainBinding
import com.example.technipassignmnet.data.network.RetrofitService
import com.example.technipassignmnet.domain.repository.MovieRepository
import com.example.technipassignmnet.utils.Status
import com.example.technipassignmnet.presentation.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        movieViewModel =
            ViewModelProvider(this, MyViewModelFactory(MovieRepository(retrofitService))).get(
                MovieViewModel::class.java
            )

        movieViewModel.movieLiveData.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            when(it.status) {
                Status.SUCCESS -> {
                    movieAdapter.setMovieList(it.data!!)
                    binding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(this,it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })

        movieViewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })

        movieViewModel.getTopRatedMoviesList()
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext,2)
            adapter = movieAdapter
        }
    }

}