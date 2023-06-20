package com.example.technipassignmnet.data.model
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: List<Results> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results" )
    var totalResults : Int? = null
)
