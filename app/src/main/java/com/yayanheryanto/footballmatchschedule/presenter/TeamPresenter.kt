package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.LeagueResponse
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getAllLeague() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getAllLeague()).await(),
                LeagueResponse::class.java
            )

            view.hideLoading()
            view.showLeagueList(data.leagues)
        }
    }

    fun getTeamLeague(leagueId:String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeamLeague(leagueId)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamLeague(data.teams)
        }
    }

    fun getTeamSearch(teamName:String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getSearchTeam(teamName)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamSearch(data.teams)
        }
    }
}