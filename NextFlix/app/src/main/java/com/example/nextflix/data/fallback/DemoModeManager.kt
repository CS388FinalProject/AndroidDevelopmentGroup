package com.example.nextflix.data.fallback

object DemoModeManager {

    var isDemoMode: Boolean = true

    fun enableDemo() {
        isDemoMode = true
    }

    fun disableDemo() {
        isDemoMode = false
    }
}