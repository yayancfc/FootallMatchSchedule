package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.model.Team
import kotlinx.android.synthetic.main.fragment_overview.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class OverviewFragment : Fragment() {

    private var teams: Team? = null

    companion object {
        private const val DATA_TEAM = "data_team"
        fun newInstance(team: Team): OverviewFragment {
            val fragment = OverviewFragment()
            val args = Bundle()
            args.putParcelable(DATA_TEAM, team)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            teams = arguments!!.getParcelable(DATA_TEAM)
            overview.text = teams?.strDescriptionEN
        }
    }


}
