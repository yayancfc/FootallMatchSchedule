package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.Match

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}