package com.yayanheryanto.footballmatchschedule.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.model.Player
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initData()
    }

    private fun initData() {

        val player: Player? = intent.getParcelableExtra("player")
        Log.d("Player", player?.strPlayer)
        player_weight.text = player?.strWeight
        player_height.text = player?.strHeight
        player_description.text = player?.strDescriptionEN
        player_position.text = player?.strPosition
        player_name.text = player?.strPlayer
        Glide.with(applicationContext).load(player?.strThumb).into(player_image)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}
