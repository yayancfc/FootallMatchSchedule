package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.PlayerResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getAllPlayer(teamId:String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getAllPlayer(teamId)).await(),
                PlayerResponse::class.java
            )

            view.hideLoading()
            view.showAllPlayer(data.players)
        }
    }

}