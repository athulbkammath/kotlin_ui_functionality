package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.adapter.MovieAdapter
import com.eto.binding_usage_project.databinding.FragmentMovieListHiltBinding
import com.eto.binding_usage_project.repository.ApiRepository
import com.eto.binding_usage_project.response.MovieListModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class MovieListHilt : Fragment() {

    private lateinit var binding: FragmentMovieListHiltBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListHiltBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            progressBar.visibility = View.VISIBLE
            bvBack.visibility = View.GONE
            apiRepository.getPopularMovies(1).enqueue(object : Callback<MovieListModel> {
                override fun onResponse(
                    call: Call<MovieListModel>,
                    response: Response<MovieListModel>
                ) {
                    progressBar.visibility = View.GONE
                    when (response.code()) {
                        200 -> {
                            response.body()?.results?.let { itBody ->
                                if (itBody.isNotEmpty()) {
                                    movieAdapter.differ.submitList(itBody)
                                    rvMovieListHilt.apply {
                                        layoutManager = LinearLayoutManager(requireContext())
                                        adapter = movieAdapter
                                    }
                                    rvMovieListHilt.visibility = View.VISIBLE
                                } else {
                                    rvMovieListHilt.visibility = View.GONE
                                }
                                bvBack.visibility = View.VISIBLE
                            }

                        }

                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "The resource you requested could not be found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        401 -> {
                            Toast.makeText(
                                requireContext(),
                                "Invalid API key: You must be granted a valid key.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                override
                fun onFailure(call: Call<MovieListModel>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
            binding.bvBack.setOnClickListener {
                findNavController().navigate(R.id.action_movieListHilt_to_fragment_home_page)
            }
        }
    }


}