package com.eto.binding_usage_project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.ItemLayoutBinding
import com.eto.binding_usage_project.response.Results
import com.eto.binding_usage_project.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NewApi")
        fun bind(item: Results) {
            binding.apply {
                tvMovieName.text = item.title
                tvLang.text = item.originalLanguage
                tvRate.text = String.format(
                    locale = context.resources.configuration.locales[0],
                    "%.1f",
                    item.voteAverage
                )
                val inputDate = item.releaseDate
                val parsedDate =
                    LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                tvMovieDateRelease.text = formattedDate


                val moviePosterURL = Constants.POST_BASEURL + item.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    error(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(
            oldItem: Results,
            newItem: Results
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Results,
            newItem: Results
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
