package com.eto.binding_usage_project.repository

import com.eto.binding_usage_project.api.ApiService
import javax.inject.Inject


class ApiRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getPopularMovies(page: Int) = apiService.getPopularMovies(page)
}