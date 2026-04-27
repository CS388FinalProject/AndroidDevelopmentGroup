package com.example.nextflix

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nextflix.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeTestRule

@RunWith(AndroidJUnit4::class)
class MovieFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun fullMovieFlowTest() {

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()


        composeTestRule.onNodeWithText("Start").performClick()


        composeTestRule.onNodeWithText("Action").performClick()
        composeTestRule.onNodeWithText("Next").performClick()


        composeTestRule.waitUntilExists(hasText("Recommended Movies"), 5000)


        composeTestRule.onAllNodes(hasTestTag("movieItem"))[0].performClick()


        composeTestRule.onNodeWithText("Save").assertIsDisplayed()


        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithContentDescription("Saved").performClick()


        composeTestRule.onNode(hasTestTag("savedMovieList"))
            .assertExists()
    }


    fun ComposeTestRule.waitUntilExists(
        matcher: SemanticsMatcher,
        timeout: Long = 5000
    ) {
        waitUntil(timeout) {
            onAllNodes(matcher).fetchSemanticsNodes().isNotEmpty()
        }
    }
}