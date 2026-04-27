package com.example.nextflix.data.fallback

import kotlinx.coroutines.delay

class FallbackRepository {

    suspend fun getMovies(): List<SampleMovie> {
        return try {
            delay(300)
            throw Exception("API down") // simulate failure
        } catch (e: Exception) {
            SampleMovieData.movies
        }
    }

    suspend fun getBooks(): List<SampleBook> {
        return try {
            delay(300)
            throw Exception("API down")
        } catch (e: Exception) {
            SampleBookData.books
        }
    }
}