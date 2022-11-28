package com.artemiod.challengeandroidkotlinalkemy.domain

import com.artemiod.challengeandroidkotlinalkemy.data.MovieRepository
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieDetailsItem
import javax.inject.Inject

class GetDetails @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getDetails(id: Int): MovieDetailsItem? {
        return movieRepository.getDetailsMovie(id)
    }
}