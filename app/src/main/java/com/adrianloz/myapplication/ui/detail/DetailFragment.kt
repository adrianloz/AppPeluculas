package com.adrianloz.myapplication.ui.detail

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.adrianloz.myapplication.R
import com.adrianloz.myapplication.adapters.GenreAdapter
import com.adrianloz.myapplication.databinding.FragmentDetailBinding
import com.adrianloz.myapplication.models.Result
import com.adrianloz.myapplication.utils.Helper
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val mViewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var result: Result
    private var genres: ArrayList<String>? = null
    private var urlVideo : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        genres = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.bind(view)
        result = DetailFragmentArgs.fromBundle(requireArguments()).result
        bindView()
        mViewModel.getData(result.id)
        setUpObservers()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpObservers() {
        mViewModel.videoMutaleLiveData.observe(viewLifecycleOwner) {
                urlVideo = it.get(0).key
            Log.e("urlVideo", urlVideo.toString())
        }
    }

    private fun bindView() {
        result.backdrop_path?.let { Helper.loadImage(requireContext(), it, binding.coverImage) }
        result.poster_path?.let { Helper.loadImage(requireContext(), it, binding.posterImage) }
        result.genre_ids?.let { Helper.getGenresFromIds(it)?.let { genres!!.addAll(it) } }
        binding.rating.text = result.vote_average.toString() + ""
        binding.title.text = result.title
        binding.topTitle.text = result.title
        binding.overView.text = result.overview
        binding.voters.text = result.vote_count.toString() + ""
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.generesRecyclerview.layoutManager = layoutManager
        val adapter = genres?.let { GenreAdapter(it) }
        binding.generesRecyclerview.adapter = adapter
        binding.backButton.setOnClickListener { closeFragment() }

        binding.playVideo.setOnClickListener {
            if (getConnection()){
                if(urlVideo != null){
                    val intent = Intent(activity, VideoActivity::class.java)
                    intent.putExtra("url", urlVideo)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getConnection(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}