package com.yayanheryanto.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.db.FavoriteMatch
import com.yayanheryanto.footballmatchschedule.util.changeFormatDate
import com.yayanheryanto.footballmatchschedule.util.formatTime
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.strToDate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_match.*

class MatchFavoriteAdapter(private val context: Context,
                   private val match: List<FavoriteMatch>,
                   private val listener: (FavoriteMatch) -> Unit)
    : RecyclerView.Adapter<MatchFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavoriteViewHolder {
        return MatchFavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.card_match, parent, false))
    }

    override fun onBindViewHolder(holder: MatchFavoriteViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

    override fun getItemCount(): Int  = match.size
}

class MatchFavoriteViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindItem(items: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

        notif_icon.invisible()
        val date = strToDate(items.matchDate)
        match_date.text = changeFormatDate(date).toString()
        match_time.text = formatTime(items.matchTime)
        tvHomeTeam.text = items.homeTeamName
        tvHomeScore.text = items.homeScore
        tvAwayTeam.text = items.awayTeamName
        tvAwayScore.text = items.awayScore
        containerView.setOnClickListener { listener(items) }
    }


}