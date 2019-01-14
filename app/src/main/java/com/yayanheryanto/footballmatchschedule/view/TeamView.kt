package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.Team


interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamLeague(data: List<Team>)
    fun showLeagueList(data: List<League>)
    fun showTeamSearch(data: List<Team>)
}