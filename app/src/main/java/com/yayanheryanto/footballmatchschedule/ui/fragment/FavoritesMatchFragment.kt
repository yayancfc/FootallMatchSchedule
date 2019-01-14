package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yayanheryanto.footballmatchschedule.ui.activity.DetailMatchActivity
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.MatchFavoriteAdapter
import com.yayanheryanto.footballmatchschedule.db.FavoriteMatch
import com.yayanheryanto.footballmatchschedule.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesMatchFragment : Fragment(){

    private var match: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: MatchFavoriteAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorites_match, container, false)

        swipeRefresh = rootView.find(R.id.swipeRefresh) as SwipeRefreshLayout
        listTeam = rootView.find(R.id.rvFavotiteMatch) as RecyclerView

        listTeam.layoutManager = LinearLayoutManager(ctx)
        adapter = MatchFavoriteAdapter(ctx, match){
            startActivity<DetailMatchActivity>("matchId" to "${it.matchId}",
                "idHomeTeam" to "${it.homeTeamId}",
                "idAwayTeam" to "${it.awayTeamId}")
        }

        listTeam.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            showFavorite()
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        match.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val data = result.parseList(classParser<FavoriteMatch>())
            match.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }



}
