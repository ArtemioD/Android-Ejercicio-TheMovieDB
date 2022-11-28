package com.artemiod.challengeandroidkotlinalkemy.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailsModel(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genres") val genres: List<Gender>,
    @SerializedName("title") val title: String,
    @SerializedName("budget") val budget: Int,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String
)

data class Gender(
    @SerializedName("name") val name: String
)
