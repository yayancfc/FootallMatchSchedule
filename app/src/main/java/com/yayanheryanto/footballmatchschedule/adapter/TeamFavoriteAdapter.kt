package com.yayanheryanto.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.db.FavoriteTeam
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_club.*

class TeamFavoriteAdapter(private val context: Context,
                          private val team: List<FavoriteTeam>,
                          private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<TeamFavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamFavoriteViewHolder {
        return TeamFavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.card_club, parent, false))
    }

    override fun onBindViewHolder(holder: TeamFavoriteViewHolder, position: Int) {
        holder.bindItem(team[position], listener)
    }

    override fun getItemCount(): Int  = team.size
}

class TeamFavoriteViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindItem(items: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        name.text = items.teamName
        Glide.with(containerView).load(items.teamBadge).into(image)
        containerView.setOnClickListener { listener(items) }
    }


}