package com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemiod.challengeandroidkotlinalkemy.domain.GetMovies
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ApiStatus {LOADING, ERROR, DONE}

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMovies: GetMovies) : ViewModel() {

    private var _moviesList = MutableLiveData<MutableList<MovieItem>>()
    val moviesList: LiveData<MutableList<MovieItem>>
        get() = _moviesList

    var page = 1

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getAllMovies(page)
    }

    fun getAllMovies(page: Int) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                //Log.d("tag", "llamada...")
                if (page == 1) {
                    _moviesList.value = getMovies.getAll(page)
                } else {
                    _moviesList.value?.addAll(getMovies.getAll(page))
                }
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                //Log.d("tag", "${e.message}")
                _status.value = ApiStatus.ERROR
            }
            //Log.d("tag", "fin...")
        }

    }
}