package com.yayanheryanto.footballmatchschedule.ui.activity

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.TeamPagerAdapter
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.db.FavoriteTeam
import com.yayanheryanto.footballmatchschedule.db.teamDatabase
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.presenter.DetailTeamPresenter
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.DetailTeamView
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    lateinit var team: Team
    private lateinit var presenter: DetailTeamPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = find(R.id.progressBar) as ProgressBar
        //swipeRefresh = find(R.id.swipeRefresh) as SwipeRefreshLayout

        val teamId = intent.getSerializableExtra("teamId")
        favoriteState(teamId.toString())

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this@DetailTeamActivity, request, gson)
        presenter.getTeamDetail(teamId.toString())
//        swipeRefresh.setOnRefreshListener {
//            presenter.getTeamDetail(teamId.toString())
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.add_to_favorite -> {
                if (isFavorite)
                    removeFavoriteMatch()
                else
                    addFavoriteMatch()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun favoriteState(matchId: String) {
        teamDatabase.use {
            val result = select(FavoriteTeam.TABLE_FAVOURITE_TEAM)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to matchId
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
            setFavorite()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_added_to_favorites
            )
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_add_to_favorites
            )
    }

    private fun addFavoriteMatch() {
        try {
            teamDatabase.use {
                insert(
                    FavoriteTeam.TABLE_FAVOURITE_TEAM,
                    FavoriteTeam.TEAM_ID to team.teamId,
                    FavoriteTeam.STR_TEAM to team.teamName,
                    FavoriteTeam.STR_TEAM_BADGE to team.teamBadge
                )
            }
            snackbar(coordinator_layout, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(coordinator_layout, e.localizedMessage).show()
        }
    }

    private fun removeFavoriteMatch() {
        try {
            teamDatabase.use {
                delete(FavoriteTeam.TABLE_FAVOURITE_TEAM, "(TEAM_ID = {id})","id" to team.teamId.toString())
            }
            snackbar(coordinator_layout, "Remove from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(coordinator_layout, e.localizedMessage).show()
        }
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamLeague(data: List<Team>) {
        team = data.single()
        Glide.with(this).load(data.single().teamBadge).into(teamBadge)
        teamName.text = data.single().teamName
        teamYears.text = data.single().intFormedYear.toString()
        teamStadium.text = data.single().strStadium


        val pageAdapter = TeamPagerAdapter(supportFragmentManager, data.single())
        viewPager.adapter = pageAdapter
        tab_layout.setupWithViewPager(viewPager)
    }

}
