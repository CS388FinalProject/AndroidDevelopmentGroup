package com.example.nextflix.ui.state

object StateHandler {

    fun <T> handleData(data: List<T>): UiState<List<T>> {
        return when {
            data.isEmpty() -> UiState.Empty
            else -> UiState.Success(data)
        }
    }

    fun error(message: String): UiState.Error {
        return UiState.Error(message)
    }

    fun loading(): UiState.Loading {
        return UiState.Loading
    }
}