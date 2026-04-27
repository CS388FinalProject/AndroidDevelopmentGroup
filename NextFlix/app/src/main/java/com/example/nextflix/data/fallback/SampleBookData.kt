package com.example.nextflix.data.fallback

data class SampleBook(
    val id: Int,
    val title: String,
    val author: String,
    val description: String
)

object SampleBookData {
    val books = listOf(
        SampleBook(1, "Fallback Book 1", "Unknown", "Offline book"),
        SampleBook(2, "Fallback Book 2", "Unknown", "Backup book data")
    )
}