package com.yayanheryanto.footballmatchschedule.presenter

import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.api.TheSportDBApi
import com.yayanheryanto.footballmatchschedule.model.SearchMatchResponse
import com.yayanheryanto.footballmatchschedule.util.CoroutineContextProvider
import com.yayanheryanto.footballmatchschedule.view.SearchMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(
    private val view: SearchMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getSearchMatch(matchName: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getSearchMatch(matchName)).await(),
                SearchMatchResponse::class.java
            )

            view.hideLoading()
            view.showMatchList(data.events)
        }

    }
}
