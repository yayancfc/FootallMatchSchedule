package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.MatchResponse
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.DetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenter(
    private val view: DetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatchDetail(idEvent: String?, teamHomeId: String?, teamAwayId: String) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val matchDetail = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getMatchDetail(idEvent)).await(),
                MatchResponse::class.java
            )

            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeambyId(teamHomeId)).await(),
                TeamResponse::class.java
            )

            val data2 = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeambyId(teamAwayId)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showMatchDetail(matchDetail.events[0], data.teams, data2.teams)

        }
    }


}