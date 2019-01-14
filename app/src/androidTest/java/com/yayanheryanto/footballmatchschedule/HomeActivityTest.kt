package com.yayanheryanto.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.yayanheryanto.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTestUI{
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour() {

        Thread.sleep(3000)
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(matches)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withText("NEXT")).perform(click())

        Thread.sleep(3000)
        onView(withText("PAST")).perform(click())
        onView(withId(rvLastMatch)).check(matches(isDisplayed()))
        onView(withId(spinner_list_league)).check(matches(isDisplayed()))
        onView(withId(spinner_list_league)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(3000)
        onView(withId(rvLastMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvLastMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withId(teams)).perform(click())

        Thread.sleep(3000)
        onView(withId(spinner_list_league)).check(matches(isDisplayed()))
        onView(withId(spinner_list_league)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        Thread.sleep(3000)
        onView(withText("Barcelona")).perform(click())

        Thread.sleep(3000)
        onView(withText("Players")).perform(click())

        Thread.sleep(3000)
        onView(withId(rvPlayer)).check(matches(isDisplayed()))
        onView(withId(rvPlayer)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvPlayer)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))


        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withId(favoritesMatch)).perform(click())

        Thread.sleep(3000)
        onView(withId(rvFavotiteMatch)).check(matches(isDisplayed()))
        onView(withId(rvFavotiteMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvFavotiteMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
        onView(withText("TEAMS")).perform(click())

        Thread.sleep(3000)
        onView(withId(rvFavotiteTeam)).check(matches(isDisplayed()))
        onView(withId(rvFavotiteTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(rvFavotiteTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        Thread.sleep(3000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        Thread.sleep(3000)
        pressBack()

        Thread.sleep(3000)
    }
}