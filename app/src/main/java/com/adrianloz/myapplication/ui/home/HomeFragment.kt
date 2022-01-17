package com.adrianloz.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianloz.myapplication.adapters.HomePageAdapter
import com.adrianloz.myapplication.databinding.FragmentHomeBinding
import com.adrianloz.myapplication.models.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomePageAdapter.onHomePageItemClickListener{
    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var nowPlayingAdapter: HomePageAdapter
    private lateinit var popularAdapter: HomePageAdapter
    private lateinit var nowPlayingList: MutableList<Result>
    private lateinit var popularList: MutableList<Result>
    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlayingList = mutableListOf()
        popularList = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRootView()
        setUpObservers()
        // Inflate the layout for this fragment
        return binding!!.root
    }

    private fun initRootView(){
        val l1 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val l2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.nowPlayingRecyclerview.layoutManager = l1
        binding.mostPopularRecyclerview.layoutManager = l2
        nowPlayingAdapter = HomePageAdapter(nowPlayingList!!, requireContext(),this, 0)
        popularAdapter = HomePageAdapter(popularList!!, requireContext(), this, 0)
        binding.nowPlayingRecyclerview.adapter = nowPlayingAdapter
        binding.mostPopularRecyclerview.adapter = popularAdapter
        binding.mostPopularRecyclerview.isNestedScrollingEnabled = false
        binding.nowPlayingRecyclerview.isNestedScrollingEnabled = false
    }
    private fun setUpObservers() {

        mViewModel.nowPlayingMutableLiveData.observe(viewLifecycleOwner) { results ->
            nowPlayingList!!.clear()
            nowPlayingList!!.addAll(results)
            nowPlayingAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = nowPlayingList.size != 0
            binding.noResultsLayout.isVisible = nowPlayingList.size == 0
        }
        mViewModel.popularMutableLiveData.observe(viewLifecycleOwner) { results ->
            popularList!!.clear()
            popularList!!.addAll(results)
            popularAdapter.notifyDataSetChanged()
            binding.homeScroll.isVisible = popularList.size != 0
            binding.noResultsLayout.isVisible = popularList.size == 0
        }

        binding.retryButton.setOnClickListener { v -> mViewModel.getData() }
        mViewModel.loading.observe(viewLifecycleOwner, {
            binding.progressbar.isVisible = it != null && it
        })

        mViewModel.msg.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(result: Result?) {
        result?.let {
            val action: NavDirections =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

}