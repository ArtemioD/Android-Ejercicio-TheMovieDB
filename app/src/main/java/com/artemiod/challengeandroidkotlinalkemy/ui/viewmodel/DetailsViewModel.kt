package com.artemiod.challengeandroidkotlinalkemy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemiod.challengeandroidkotlinalkemy.domain.GetDetails
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieDetailsItem
import com.artemiod.challengeandroidkotlinalkemy.ui.view.DetailsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetails: GetDetails) : ViewModel() {

    private var _movieDetails = MutableLiveData<MovieDetailsItem>()
    val movieDetails: LiveData<MovieDetailsItem> get() = _movieDetails

    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    init {
        getAllDetails(DetailsFragment.idMovie)
    }

    private fun getAllDetails(id: Int) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                _movieDetails.value = getDetails.getDetails(id)
                _status.value = ApiStatus.DONE
                Log.d("tag", "${movieDetails.value}")
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Log.d("tag", "${e.message}")
            }

        }

    }
}