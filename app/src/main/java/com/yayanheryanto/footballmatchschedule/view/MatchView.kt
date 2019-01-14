package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
    fun showLeagueList(data: List<League>)
}