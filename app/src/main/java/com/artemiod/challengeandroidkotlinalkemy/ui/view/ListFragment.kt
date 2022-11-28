package com.artemiod.challengeandroidkotlinalkemy.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artemiod.challengeandroidkotlinalkemy.core.Credentials
import com.artemiod.challengeandroidkotlinalkemy.databinding.FragmentListBinding
import com.artemiod.challengeandroidkotlinalkemy.domain.GetMovies
import com.artemiod.challengeandroidkotlinalkemy.domain.SelectedListener
import com.artemiod.challengeandroidkotlinalkemy.ui.view.adapters.ItemAdapter
import com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel.ApiStatus
import com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ClassCastException
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var getMovies: GetMovies
    @Inject lateinit var adapter: ItemAdapter

    private lateinit var listener: SelectedListener

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as SelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar el listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerViewMovies
        recyclerView.adapter = adapter

        observeListMovie()
        observeApiStatus()
        nextPageMovies()
        onClickItem()
        //test()
    }

    private fun nextPageMovies() {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount

                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()

                    if (viewModel.status.value != ApiStatus.LOADING) {
                        if ((visibleItemCount + lastVisiblePosition) >= totalItemCount - 6) {
                            if (viewModel.page < Credentials.pagesTotal) {
                                viewModel.page++
                            }
                            //Log.d("tag", "${viewModel.page}")
                            viewModel.getAllMovies(viewModel.page)
                        }
                    }
                }
            }
        })
    }

    private fun observeApiStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.statusOffline.visibility = View.GONE
                    if (viewModel.page == 1) {
                        binding.shimmerLoading.visibility = View.VISIBLE
                        binding.recyclerViewMovies.visibility =View.GONE
                    }
                }
                ApiStatus.ERROR -> {
                    binding.statusOffline.visibility = View.VISIBLE
                    binding.shimmerLoading.visibility = View.GONE
                    binding.recyclerViewMovies.visibility =View.GONE
                }
                ApiStatus.DONE -> {
                    binding.statusOffline.visibility = View.GONE
                    binding.shimmerLoading.visibility = View.GONE
                    binding.recyclerViewMovies.visibility =View.VISIBLE
                }
            }
        }
    }

    private fun observeListMovie() {
        viewModel.moviesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onClickItem() {
        adapter.onItemClickListener = { movie ->
            //Toast.makeText(requireContext(), movie.id.toString(), Toast.LENGTH_LONG).show()
            listener.onSelected(movie.id)
        }
    }

    private fun test() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("tag", "llamada...")
            //val res = getMovies.getAll(1)
            //Log.d("tag", "$res")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}