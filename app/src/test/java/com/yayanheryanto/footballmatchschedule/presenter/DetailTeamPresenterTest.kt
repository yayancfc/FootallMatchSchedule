package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.DetailTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailTeamPresenterTest {

    @Mock
    private lateinit var view: DetailTeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: DetailTeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailTeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamDetail() {
        val teams: MutableList<Team> = mutableListOf()
        val teamResponse = TeamResponse(teams)
        val teamId = "133610"

        GlobalScope.launch {

            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeambyId(teamId)).await(),
                    TeamResponse::class.java
                )
            ).thenReturn(teamResponse)

            presenter.getTeamDetail(teamId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeamLeague(teams)
            Mockito.verify(view).hideLoading()
        }
    }
}