package com.yayanheryanto.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyTeamDatabaseOpenHelper(context: Context): ManagedSQLiteOpenHelper(context,"FootballTeam.db",null,1) {

    companion object {
        private var  instance : MyTeamDatabaseOpenHelper? = null
        @Synchronized
        fun getInstance(context: Context):MyTeamDatabaseOpenHelper {
            if (instance == null) {
                instance = MyTeamDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyTeamDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteTeam.TABLE_FAVOURITE_TEAM,true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.STR_TEAM to TEXT,
            FavoriteTeam.STR_TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteTeam.TABLE_FAVOURITE_TEAM,true)
    }

}
val Context.teamDatabase: MyTeamDatabaseOpenHelper
    get() = MyTeamDatabaseOpenHelper.getInstance(applicationContext)