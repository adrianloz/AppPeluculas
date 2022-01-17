package com.adrianloz.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adrianloz.myapplication.databinding.HomePageItemBinding
import com.adrianloz.myapplication.models.Result
import com.adrianloz.myapplication.utils.Helper

class HomePageAdapter (
    private val results : MutableList<Result>,
    private val context: Context,
    private val listener : onHomePageItemClickListener?,
    val type : Int
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: HomePageItemBinding =
            HomePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomePageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is HomePageViewHolder) {
                val result = results[position]
                holder.bindData(result)
            }
    }


    override fun getItemCount(): Int {
        return results.size
    }

   inner class HomePageViewHolder(binding: HomePageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: HomePageItemBinding = binding
        fun bindData(result: Result) {
            result.poster_path?.let { Helper.loadImage(context, it, binding.imdbPoster) }
            if (result.adult) {
                binding.eighteenPlus.visibility = View.VISIBLE
            }
            binding.title.text = result.title
            if (result.release_date != null) {
                binding.year.text = "${result.release_date}"
            } else {
                binding.year.text = "-"
            }

            binding.rating.text = result.vote_average.toString() + ""
        }

        init {
            binding.imdbPoster.setOnClickListener { v ->
                val position = this@HomePageViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(results[position])
                }
            }
        }
    }

    interface onHomePageItemClickListener {
        fun onItemClick(result: Result?)
    }
}