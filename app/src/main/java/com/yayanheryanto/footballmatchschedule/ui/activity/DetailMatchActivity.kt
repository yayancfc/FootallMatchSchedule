package com.yayanheryanto.footballmatchschedule.ui.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.db.FavoriteMatch
import com.yayanheryanto.footballmatchschedule.db.database
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.presenter.DetailPresenter
import com.yayanheryanto.footballmatchschedule.util.changeFormatDate
import com.yayanheryanto.footballmatchschedule.util.formatTime
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.DetailView
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find


class DetailMatchActivity : AppCompatActivity(), DetailView {

    lateinit var match: Match
    private lateinit var presenter: DetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBar = find(R.id.progressBar) as ProgressBar
        swipeRefresh = find(R.id.swipeRefresh) as SwipeRefreshLayout

        val matchId = intent.getSerializableExtra("matchId")
        val idHomeTeam = intent.getSerializableExtra("idHomeTeam")
        val idAwayTeam = intent.getSerializableExtra("idAwayTeam")
        favoriteState(matchId.toString())

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this,request,gson)
        presenter.getMatchDetail(matchId.toString(), idHomeTeam.toString(), idAwayTeam.toString())

        swipeRefresh.setOnRefreshListener {
            presenter.getMatchDetail(matchId.toString(), idHomeTeam.toString(), idAwayTeam.toString())
        }


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

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchDetail(matchDetail: Match, data: List<Team>, data2: List<Team>) {
        match = matchDetail
        swipeRefresh.isRefreshing = false

        date.text = changeFormatDate(matchDetail.matchDate)
        match_time.text = formatTime(matchDetail.matchTime)
        team_home.text = matchDetail.homeTeam?:""
        score_home.text = matchDetail.homeScore?:""
        home_formation.text = matchDetail.homeFormation?:""
        home_goal.text = parserGoal(matchDetail.homeGoals?:"")
        home_shot.text = matchDetail.homeShots?:""
        home_goalkeeper.text = parserGoal(matchDetail.homeGoalKeeper?:"")
        home_defense.text = parser(matchDetail.homeDefence?:"")
        home_midlefield.text = parser(matchDetail.homeMidfield?:"")
        home_forward.text = parser(matchDetail.homeForward?:"")
        home_subtituties.text = parser(matchDetail.homeSubstitutes?:"")

        team_away.text = matchDetail.awayTeam?:""
        score_away.text = matchDetail.awayScore?:""
        away_formation.text = matchDetail.awayFormation?:""
        away_goal.text = parserGoal(matchDetail.awayGoals?:"")
        away_shot.text = matchDetail.awayShots?:""
        away_goalkeeper.text = parserGoal(matchDetail.awayGoalKeeper?:"")
        away_defense.text = parser(matchDetail.awayDefence?:"")
        away_midlefield.text = parser(matchDetail.awayMidfield?:"")
        away_forward.text = parser(matchDetail.awayForward?:"")
        away_subtituties.text = parser(matchDetail.awaySubtitutes?:"")
        Glide.with(this).load(data.single().teamBadge).into(logo_home)
        Glide.with(this).load(data2.single().teamBadge).into(logo_away)
    }

    private fun favoriteState(matchId:String){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs("(MATCH_ID = {id})",
                    "id" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
            setFavorite()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_added_to_favorites
            )
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_add_to_favorites
            )
    }

    private fun addFavoriteMatch(){
        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.MATCH_ID to match.matchId,
                    FavoriteMatch.MATCH_DATE to match.matchDate.toString(),
                    FavoriteMatch.HOME_TEAM_ID to match.idHomeTeam,
                    FavoriteMatch.HOME_TEAM_NAME to match.homeTeam,
                    FavoriteMatch.HOME_TEAM_SCORE to match.homeScore,
                    FavoriteMatch.AWAY_TEAM_ID to match.idAwayTeam,
                    FavoriteMatch.AWAY_TEAM_NAME to match.awayTeam,
                    FavoriteMatch.AWAY_TEAM_SCORE to match.awayScore,
                    FavoriteMatch.MATCH_TIME to match.matchTime
                )
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFavoriteMatch(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})","id" to match.matchId.toString())
            }
            snackbar(swipeRefresh, "Remove from favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

}