package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.model.Team

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail (matchDetail: Match, data : List<Team>, data2 : List<Team>)
}