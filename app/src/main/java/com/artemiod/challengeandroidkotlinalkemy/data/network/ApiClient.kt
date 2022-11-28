package com.artemiod.challengeandroidkotlinalkemy.data.network

import com.artemiod.challengeandroidkotlinalkemy.core.Credentials
import com.artemiod.challengeandroidkotlinalkemy.data.model.ListMoviesModel
import com.artemiod.challengeandroidkotlinalkemy.data.model.MovieDetailsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    // https://api.themoviedb.org/3/movie/popular?api_key=74f883285237af1c1826a26de0f76970&language=en-us&page=2
    @GET(value = "3/movie/popular?")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = Credentials.API_KEY,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ): Response<ListMoviesModel>

    // 3/movie/343611?api_key=74f883285237af1c1826a26de0f76970
    @GET("3/movie/{movieId}?")
    suspend fun getMovieId(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = Credentials.API_KEY,
        @Query("language") language: String = "es-ES",
    ): Response<MovieDetailsModel>
}