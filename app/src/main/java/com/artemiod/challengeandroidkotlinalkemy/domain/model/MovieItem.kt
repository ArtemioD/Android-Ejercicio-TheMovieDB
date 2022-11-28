package com.artemiod.challengeandroidkotlinalkemy.domain.model

import com.artemiod.challengeandroidkotlinalkemy.core.Credentials
import com.artemiod.challengeandroidkotlinalkemy.data.model.ListMoviesModel
import com.artemiod.challengeandroidkotlinalkemy.data.model.ResultApi

data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String,
)

fun ResultApi.toDomain(): MovieItem {
    val poster = "${Credentials.PATH_IMG}${this.posterPath}"
    return MovieItem(id, title, poster)
}