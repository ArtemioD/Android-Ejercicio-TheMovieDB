package com.artemiod.challengeandroidkotlinalkemy.domain

import com.artemiod.challengeandroidkotlinalkemy.data.MovieRepository
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieItem
import javax.inject.Inject

class GetMovies @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getAll(page: Int): MutableList<MovieItem> {
        return movieRepository.getMovies(page)
    }
}