package com.artemiod.challengeandroidkotlinalkemy.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.artemiod.challengeandroidkotlinalkemy.R
import com.artemiod.challengeandroidkotlinalkemy.databinding.FragmentDetailsBinding
import com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel.ApiStatus
import com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idMovie = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        observeStatus()
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.appBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                    binding.statusOffline.visibility = View.GONE
                }
                ApiStatus.DONE -> {
                    binding.progressBar.visibility = View.GONE
                    binding.appBar.visibility = View.VISIBLE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.statusOffline.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.appBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                    binding.statusOffline.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observe() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {

            val img = if (it.backdropPath.isNullOrEmpty()) {
                it.posterPath
            } else {
                it.backdropPath
            }
            Picasso.get().load(img)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(binding.image)

            if (it.overview.isNullOrEmpty()) {
                binding.tvOverviewEmpty.visibility = View.VISIBLE
                binding.tvOverview.visibility = View.GONE
            } else {
                binding.tvOverviewEmpty.visibility = View.GONE
                binding.tvOverview.visibility = View.VISIBLE
                binding.tvOverview.text = it.overview
            }
            binding.collapsingToolbar.title = it.title
            binding.tvPopularity.text = it.popularity.toString()
            binding.tvReleaseData.text = it.releaseDate
            binding.tvBudget.text = it.budget
            binding.tvGenders.text = it.genders
            (if (it.runtime.isEmpty()) {
                getString(R.string.text_empty)
            } else {
                getString(R.string.runtimeLink, it.runtime)
            }).also { st ->
                binding.tvRuntime.text = st }

            Log.d("tag", it.genders)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var idMovie = 1
    }

}