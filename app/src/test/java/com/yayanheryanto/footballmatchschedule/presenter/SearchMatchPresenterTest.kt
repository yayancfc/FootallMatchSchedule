package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.model.SearchMatchResponse
import com.yayanheryanto.footballmatchschedule.util.TestContextProvider
import com.yayanheryanto.footballmatchschedule.view.SearchMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getSearchMatch() {
        val match: MutableList<Match> = mutableListOf()
        val matchResponse = SearchMatchResponse(match)
        val matchName = "chelsea"

        GlobalScope.launch {

            Mockito.`when`(
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getSearchMatch(matchName)).await(),
                    SearchMatchResponse::class.java
                )
            ).thenReturn(matchResponse)

            presenter.getSearchMatch(matchName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(match)
            Mockito.verify(view).hideLoading()
        }
    }
}