package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.TeamResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.DetailTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter(
    private val view: DetailTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getTeamDetail(idTeam: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val teamDetail = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeambyId(idTeam)).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.showTeamLeague(teamDetail.teams)

        }
    }
}