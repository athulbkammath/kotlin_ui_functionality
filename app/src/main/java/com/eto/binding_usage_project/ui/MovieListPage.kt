package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.adapter.MovieAdapter
import com.eto.binding_usage_project.api.ApiClient
import com.eto.binding_usage_project.api.ApiService
import com.eto.binding_usage_project.databinding.FragmentMovieListPageBinding
import com.eto.binding_usage_project.response.MovieListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListPage : Fragment() {

    private lateinit var binding: FragmentMovieListPageBinding
    private val movieAdapter by lazy {
        MovieAdapter()
    }
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListPageBinding.inflate(inflater, container, false)
        setUpClicks()
        return binding.root
    }


    private fun setUpClicks() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            bvBack.visibility = View.GONE
            val callMovieApi = api.getPopularMovies(1)
            callMovieApi.enqueue(object : Callback<MovieListModel> {
                override fun onResponse(
                    call: Call<MovieListModel>, response: Response<MovieListModel>
                ) {
                    progressBar.visibility = View.GONE

                    when (response.code()) {
                        in 200..299 -> {
                            val itData = response.body()?.results

                            if (!itData.isNullOrEmpty()) {
                                movieAdapter.differ.submitList(itData)

                                rvMovieList.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = movieAdapter
                                }
                                rvMovieList.visibility = View.VISIBLE
                                bvBack.visibility = View.VISIBLE
                            } else {
                                rvMovieList.visibility = View.GONE
                                bvBack.visibility = View.GONE
                            }
                            progressBar.visibility = View.GONE
                        }


                        in 300..399 -> {
                            Log.d("Response Code", "Redirection Messages: ${response.message()}")
                        }

                        in 400..499 -> {
                            Log.d("Response Code", "Client error response: ${response.message()}")
                        }

                        in 500..599 -> {

                            Log.d("Response Code", "Server error response: ${response.message()}")
                        }
                    }


                }

                override fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    rvMovieList.visibility = View.GONE
                    bvBack.visibility = View.GONE
                    Log.d("Response Code", "Err: ${t.message}")
                }
            })


        }

        binding.bvBack.setOnClickListener {
            findNavController().navigate(R.id.action_movieListPage_to_fragment_home_page)
        }
    }

}