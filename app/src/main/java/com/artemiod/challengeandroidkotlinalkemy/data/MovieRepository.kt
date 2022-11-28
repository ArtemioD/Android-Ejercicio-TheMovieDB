package com.artemiod.challengeandroidkotlinalkemy.data

import com.artemiod.challengeandroidkotlinalkemy.core.Credentials
import com.artemiod.challengeandroidkotlinalkemy.data.model.ListMoviesModel
import com.artemiod.challengeandroidkotlinalkemy.data.network.ApiService
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieDetailsItem
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieItem
import com.artemiod.challengeandroidkotlinalkemy.domain.model.toDomain
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getMovies(page: Int): MutableList<MovieItem> {
        val response = apiService.getMovies(page)
        Credentials.pagesTotal = response?.pTotal() ?: 1
        return response?.results?.map { it.toDomain() } as MutableList<MovieItem>
    }

    fun ListMoviesModel.pTotal() = totalPages

    suspend fun getDetailsMovie(id: Int): MovieDetailsItem? {
        val response = apiService.getMovieId(id)
        return response?.toDomain()
    }
}