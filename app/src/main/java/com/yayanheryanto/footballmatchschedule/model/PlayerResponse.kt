package com.yayanheryanto.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    val players: List<Player>)