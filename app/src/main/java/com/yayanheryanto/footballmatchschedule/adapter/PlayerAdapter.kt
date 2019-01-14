package com.yayanheryanto.footballmatchschedule.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yayanheryanto.footballmatchschedule.R.layout.card_player
import com.yayanheryanto.footballmatchschedule.model.Player
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_player.*

class PlayerAdapter(private val context: Context, private val items: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(card_player, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(items: Player, listener: (Player) -> Unit) {
            player_name.text = items.strPlayer
            player_position.text = items.strPosition
            Glide.with(containerView).load(items.strThumb).into(img_player)
            containerView.setOnClickListener { listener(items) }
        }
    }
}