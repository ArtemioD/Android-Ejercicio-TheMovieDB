package com.artemiod.challengeandroidkotlinalkemy.data.network

import android.util.Log
import com.artemiod.challengeandroidkotlinalkemy.data.model.ListMoviesModel
import com.artemiod.challengeandroidkotlinalkemy.data.model.MovieDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {

    //private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getMovies(page: Int): ListMoviesModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getMovies(page = page)
            Log.d("tag", "getMovies code: ${response.code()}")
            response.body()
        }
    }

    suspend fun getMovieId(id: Int): MovieDetailsModel? {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getMovieId(id)
            //Log.d("tag", "getMovieId code: ${response.code()}")
            // todo hacer algo si consulta falla y el code no es 200
            response.body()
        }
    }


}