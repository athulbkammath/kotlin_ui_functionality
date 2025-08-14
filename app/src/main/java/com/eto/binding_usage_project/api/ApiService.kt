package com.eto.binding_usage_project.api

import com.eto.binding_usage_project.response.MovieListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Call<MovieListModel>




}