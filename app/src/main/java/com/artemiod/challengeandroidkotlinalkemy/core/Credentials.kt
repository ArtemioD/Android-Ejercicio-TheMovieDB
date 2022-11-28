package com.artemiod.challengeandroidkotlinalkemy.core

object Credentials {

    // key: 74f883285237af1c1826a26de0f76970


    // https://api.themoviedb.org/3/movie/popular?api_key=74f883285237af1c1826a26de0f76970&language=en-us

    // Accessing page number:
    // api_key=74f883285237af1c1826a26de0f76970&language=es-ES&page=2

    // Getting image
    // https://image.tmdb.org/t/p/w500/

    // Query for details:
    // With the item above in hand, you can see the id of the movie is 343611.
    // You can use that id to query the movie details method
    // https://api.themoviedb.org/3/movie/343611?api_key=74f883285237af1c1826a26de0f76970

    const val BASE_URL = "https://api.themoviedb.org/"
    const val API_KEY = "74f883285237af1c1826a26de0f76970"
    const val PATH_IMG = "https://image.tmdb.org/t/p/w500"
    var pagesTotal = 0
}