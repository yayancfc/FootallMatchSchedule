package com.yayanheryanto.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yayanheryanto.footballmatchschedule.ui.fragment.FavoritesMatchFragment
import com.yayanheryanto.footballmatchschedule.ui.fragment.FavoritesTeamFragment

class FavoritePagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FavoritesMatchFragment()
            else -> FavoritesTeamFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Matches"
            else -> "Teams"
        }
    }

}