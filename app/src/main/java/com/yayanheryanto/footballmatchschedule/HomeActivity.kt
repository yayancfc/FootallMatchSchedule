package com.yayanheryanto.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yayanheryanto.footballmatchschedule.R.id.*
import com.yayanheryanto.footballmatchschedule.R.layout.activity_home
import com.yayanheryanto.footballmatchschedule.ui.fragment.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matches -> {
                    loadMatchesFragment(savedInstanceState)
                }
                teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
                favoritesMatch -> {
                    loadFavoritesFragment(savedInstanceState)
                }

            }
            true
        }
        bottom_navigation.selectedItemId = matches
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    TeamsFragment(), TeamsFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    MatchesFragment(), MatchesFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoritesFragment(), FavoritesFragment::class.java.simpleName)
                .commit()
        }
    }

}
