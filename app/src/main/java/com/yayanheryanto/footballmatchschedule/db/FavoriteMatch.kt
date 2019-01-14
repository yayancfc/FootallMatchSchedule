package com.yayanheryanto.footballmatchschedule.db

data class FavoriteMatch(
    val id: Long?,
    val matchId: String?,
    val matchDate: String?,
    val homeTeamId: String?,
    val homeTeamName: String?,
    val homeScore:String?,
    val awayTeamId: String?,
    val awayTeamName: String?,
    val awayScore:String?,
    val matchTime:String?){

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val HOME_TEAM_SCORE: String = "HOME_SCORE"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val AWAY_TEAM_SCORE: String = "AWAY_SCORE"
        const val MATCH_TIME: String = "MATCH_TIME"
    }
}