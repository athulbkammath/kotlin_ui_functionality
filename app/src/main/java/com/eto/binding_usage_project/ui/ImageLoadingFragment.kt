package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.FragmentImageLoadingBinding
import com.squareup.picasso.Picasso


class ImageLoadingFragment : Fragment() {
    private lateinit var binding: FragmentImageLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageLoadingBinding.inflate(inflater, container, false)
        setUpClicks()
        return binding.root
    }

    private fun setUpClicks() {
        binding.bvBack.setOnClickListener {
            findNavController().navigate(R.id.action_imageLoadingFragment_to_first_fragment)
        }
        picassoImageView()
        glideImageVIew()

    }

    private fun picassoImageView() {
        binding.bvPicasso.setOnClickListener {
            val imageUrlPicasso =
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSCUfrtO169MvyTyEmaqCPkYQLjRGxstGOCCrcK0wpZbIBv1Eq5Mjvb1vUmPt4jUpwM7g&usqp=CAU"
            Picasso.get().load(imageUrlPicasso)
                .into(binding.ivPicasso)
        }
    }

    private fun glideImageVIew() {
        val imageUrl =
            "https://img.freepik.com/premium-photo/wide-angle-shot-single-tree-growing-clouded-sky-sunset-surrounded-by-grass_181624-22807.jpg"
        binding.bvGlide.setOnClickListener {
            Glide.with(this)
                .load(imageUrl)
                .into(binding.ivGlide)
        }
    }


}