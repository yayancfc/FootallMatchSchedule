package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.Team

interface DetailTeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamLeague(data: List<Team>)
}