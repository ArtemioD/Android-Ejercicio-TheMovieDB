package com.artemiod.challengeandroidkotlinalkemy.domain.model

import com.artemiod.challengeandroidkotlinalkemy.core.Credentials
import com.artemiod.challengeandroidkotlinalkemy.data.model.MovieDetailsModel
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat

data class MovieDetailsItem(
    val backdropPath: String,
    val genders: String,
    val title: String,
    val overview: String,
    val budget: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val runtime: String,
)

fun MovieDetailsModel.toDomain(): MovieDetailsItem {
    val backdropPath = if (backdropPath.isNullOrEmpty()) "" else "${Credentials.PATH_IMG}${this.backdropPath}"
    val posterPath = "${Credentials.PATH_IMG}${this.posterPath}"
    val budget = if (this.budget == 0) "- - -" else getFormattedNumber(this.budget)
    val runtime = if (this.runtime == null) "" else "${this.runtime}"

    val genres = mutableListOf<String>()
    for (i in this.genres) genres.add(i.name)
    val genders = genres.joinToString(separator = ", ").plus(".")

    return MovieDetailsItem(backdropPath, genders, title,
        overview, budget, popularity, posterPath, releaseDate, runtime)
}

private fun getFormattedNumber(tip: Int): String {
    return NumberFormat.getCurrencyInstance().format(tip)
}
