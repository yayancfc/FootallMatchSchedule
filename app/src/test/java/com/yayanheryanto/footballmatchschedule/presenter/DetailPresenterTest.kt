package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.model.MatchResponse
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.DetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchDetail() {
        val detailMatch: MutableList<Match> = mutableListOf()
        val response = MatchResponse(detailMatch)
        val matchId = "576566"

        val homeTeams: MutableList<Team> = mutableListOf()
        val homeTeamResponse = TeamResponse(homeTeams)
        val homeTeamId = "133610"

        val awayTeams: MutableList<Team> = mutableListOf()
        val awayTeamResponse = TeamResponse(homeTeams)
        val awayTeamId = "133615"

        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getMatchDetail(matchId)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)

            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeambyId(homeTeamId)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(homeTeamResponse)

            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeambyId(awayTeamId)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(awayTeamResponse)

            presenter.getMatchDetail(matchId, homeTeamId, awayTeamId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(detailMatch[0], homeTeams, awayTeams)
            Mockito.verify(view).hideLoading()
        }
    }
}