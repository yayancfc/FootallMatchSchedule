package com.yayanheryanto.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.ui.fragment.OverviewFragment
import com.yayanheryanto.footballmatchschedule.ui.fragment.PlayerFragment

class TeamPagerAdapter(fm : FragmentManager, team: Team) : FragmentPagerAdapter(fm){
    private val teams:Team = team

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> OverviewFragment.newInstance(teams)
            else -> PlayerFragment.newInstance(teams)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Overview"
            else -> "Players"
        }
    }

}