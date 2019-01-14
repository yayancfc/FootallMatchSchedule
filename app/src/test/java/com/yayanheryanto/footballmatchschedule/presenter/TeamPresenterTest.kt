package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.LeagueResponse
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamPresenterTest {


    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getAllLeague() {
        val league: MutableList<League> = mutableListOf()
        val response = LeagueResponse(league)

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getAllLeague()).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getAllLeague()

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueList(league)
            Mockito.verify(view).hideLoading()
        }

    }

    @Test
    fun getTeamLeague() {
        val teams: MutableList<Team> = mutableListOf()
        val teamResponse = TeamResponse(teams)
        val leagueName = "English%20Premier%20League"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamLeague(leagueName)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            presenter.getAllLeague()

            verify(view).showLoading()
            verify(view).showTeamLeague(teams)
            verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamSearch() {
        val teams: MutableList<Team> = mutableListOf()
        val teamResponse = TeamResponse(teams)
        val teamName = "chelsea"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getSearchTeam(teamName)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            presenter.getAllLeague()

            verify(view).showLoading()
            verify(view).showTeamSearch(teams)
            verify(view).hideLoading()
        }
    }
}