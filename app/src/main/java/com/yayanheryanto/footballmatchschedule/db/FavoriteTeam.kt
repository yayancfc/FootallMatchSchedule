package com.yayanheryanto.footballmatchschedule.db

data class FavoriteTeam(
    val id: Long?,
    val teamId: String?,
    val teamName: String?,
    val teamBadge: String?){

    companion object {
        const val TABLE_FAVOURITE_TEAM: String = "TABLE_FAVOURITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID:String = "TEAM_ID"
        const val STR_TEAM:String = "STR_TEAM"
        const val STR_TEAM_BADGE:String = "STR_TEAM_BADGE"
    }
}