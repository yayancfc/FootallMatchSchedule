package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.Player
import com.yayanheryanto.footballmatchschedule.model.PlayerResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getAllPlayer() {
        val players: MutableList<Player> = mutableListOf()
        val playerResponse = PlayerResponse(players)
        val teamId = "133610"

        GlobalScope.launch {

            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getAllPlayer(teamId)).await(),
                    PlayerResponse::class.java
                )
            ).thenReturn(playerResponse)

            presenter.getAllPlayer(teamId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showAllPlayer(players)
            Mockito.verify(view).hideLoading()
        }
    }
}