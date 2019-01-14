package com.yayanheryanto.footballmatchschedule.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yayanheryanto.footballmatchschedule.R.layout.card_match
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.util.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_match.*
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter(private val context: Context,
                   private val match: List<Match>,
                   private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
            return MatchViewHolder(LayoutInflater.from(context).inflate(card_match, parent, false))
    }

    override fun getItemCount(): Int  = match.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(match[position], listener)
    }

}

class MatchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {


    var addToCal: Boolean = false

    fun bindItem(items: Match, listener: (Match) -> Unit) {
        if (items.homeScore==null) notif_icon.visible() else notif_icon.invisible()
        match_date.text = dateToStr(items.matchDate)
        tvHomeTeam.text = items.homeTeam
        tvHomeScore.text = items.homeScore
        tvAwayTeam.text = items.awayTeam
        tvAwayScore.text = items.awayScore
        match_time.text = formatTime(items.matchTime)

        notif_icon.setOnClickListener {
            if (!addToCal) {
                reminderMatch(items)
                addToCal = !addToCal
            } else {
                addToCal = !addToCal
            }
            notifIconSet()
        }


        containerView.setOnClickListener { listener(items) }
    }

    private fun notifIconSet() {
        if (addToCal)
            notif_icon.isChecked = true
        else
            notif_icon.isChecked = false
    }

    @SuppressLint("SimpleDateFormat")
    private fun reminderMatch(match: Match?) {
        val intent = Intent(Intent.ACTION_INSERT)
        val tes = dateToStr(match?.matchDate) + " " + formatTime(match?.matchTime)
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.getDefault())
        val start = sdf.parse(tes)
        val end = start.time + 5400000
        intent.type = "vnd.android.cursor.item/event"
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, "${match?.matchName}")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.time)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
        itemView.context.startActivity(intent)
    }
}