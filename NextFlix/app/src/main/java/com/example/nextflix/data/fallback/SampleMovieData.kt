package com.example.nextflix.data.fallback

data class SampleMovie(
    val id: Int,
    val title: String,
    val genre: String,
    val description: String
)

object SampleMovieData {
    val movies = listOf(
        SampleMovie(1, "Fallback Action", "Action", "Offline movie"),
        SampleMovie(2, "Fallback Drama", "Drama", "Backup movie data")
    )
}