package com.yayanheryanto.footballmatchschedule.view

import com.yayanheryanto.footballmatchschedule.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showAllPlayer(data: List<Player>)
}