package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.R.id.action_search
import com.yayanheryanto.footballmatchschedule.adapter.MatchPagerAdapter
import com.yayanheryanto.footballmatchschedule.ui.activity.SearchMatchActivity
import kotlinx.android.synthetic.main.fragment_matches.*
import org.jetbrains.anko.support.v4.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchesFragment : Fragment() {

    private var searchView : SearchView? = null
    private var searchMenu : MenuItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pageAdapter = MatchPagerAdapter(childFragmentManager)
        viewPager.adapter = pageAdapter
        tab_layout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        searchMenu = menu?.findItem(R.id.action_search)
        searchView = searchMenu?.actionView as SearchView?

    }

    override fun onResume() {
        super.onResume()
        searchView?.setIconifiedByDefault(true)
        searchMenu?.collapseActionView()
        searchView?.onActionViewExpanded()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            action_search -> {
                startActivity<SearchMatchActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
