package com.example.nextflix.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId

object TestNavigationHelper {

    fun click(id: Int) {
        onView(withId(id)).perform(click())
    }
}