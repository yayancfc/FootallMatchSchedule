package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.LeagueResponse
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.model.MatchResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLastMatchList() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val leagueId = "4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getLastMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getNextMatchList(leagueId)

            verify(view).showLoading()
            verify(view).showMatchList(teams)
            verify(view).hideLoading()
        }
    }

    @Test
    fun getNextMatchList() {
        val teams: MutableList<Match> = mutableListOf()
        val response = MatchResponse(teams)
        val leagueId = "4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getNextMatch(leagueId)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getNextMatchList(leagueId)

            verify(view).showLoading()
            verify(view).showMatchList(teams)
            verify(view).hideLoading()
        }
    }

    @Test
    fun getAllLeague() {
        val league: MutableList<League> = mutableListOf()
        val response = LeagueResponse(league)

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getAllLeague()).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)

            presenter.getAllLeague()

            verify(view).showLoading()
            verify(view).showLeagueList(league)
            verify(view).hideLoading()
        }
    }
}