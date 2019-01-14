package com.yayanheryanto.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yayanheryanto.footballmatchschedule.ui.fragment.NextMatchFragment
import com.yayanheryanto.footballmatchschedule.ui.fragment.PrefMatchFragment

class MatchPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> NextMatchFragment()
            else -> PrefMatchFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Next"
            else -> "Past"
        }
    }

}