package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.FragmentHomePageBinding


class HomePage : Fragment() {

    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
        setUpClicks()
        return binding.root
    }

    private fun setUpClicks() {

        val action = HomePageDirections.actionFirstFragmentToSecondFragment("Form Validation")
        binding.bvFormValidation.setOnClickListener {
            findNavController().navigate(action)
        }

        binding.bvImageLoad.setOnClickListener {
            findNavController().navigate(R.id.action_first_fragment_to_imageLoadingFragment)
        }

    }
}


