package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.LeagueResponse
import com.yayanheryanto.footballmatchschedule.model.MatchResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLastMatchList(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getLastMatch(leagueId)).await(),
                MatchResponse::class.java
            )

            view.hideLoading()
            view.showMatchList(data.events)
        }

    }

    fun getNextMatchList(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getNextMatch(leagueId)).await(),
                MatchResponse::class.java
            )

            view.hideLoading()
            view.showMatchList(data.events)
        }
    }

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

}
