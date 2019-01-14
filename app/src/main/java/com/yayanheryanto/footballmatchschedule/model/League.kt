package com.yayanheryanto.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague")
    var idLeague: String? = null,
    @SerializedName("strLeague")
    var strLeague: String? = null,
    @SerializedName("strSport")
    var strSport: String? = null

){
    override fun toString(): String {
        return strLeague.toString()
    }
}