package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.MatchAdapter
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.presenter.MatchPresenter
import com.yayanheryanto.footballmatchschedule.ui.activity.DetailMatchActivity
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.MatchView
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
class NextMatchFragment : Fragment(), MatchView {

    private var match: MutableList<Match> = mutableListOf()
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var item : League

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_next_match, container, false)

        swipeRefresh = rootView.find(R.id.swipeRefresh) as SwipeRefreshLayout
        progressBar = rootView.find(R.id.progressBar) as ProgressBar
        listTeam = rootView.find(R.id.rvNextMatch) as RecyclerView
        spinner = rootView.find(R.id.spinnerNextLeague) as Spinner

        listTeam.layoutManager = LinearLayoutManager(ctx)
        adapter = MatchAdapter(ctx, match){
            startActivity<DetailMatchActivity>("matchId" to "${it.matchId}",
                "idHomeTeam" to "${it.idHomeTeam}",
                "idAwayTeam" to "${it.idAwayTeam}")
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this@NextMatchFragment, request, gson)
        presenter.getAllLeague()

        swipeRefresh.setOnRefreshListener {
            presenter.getNextMatchList(item.idLeague.toString())
        }

        return rootView
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: List<League>) {
        leagues.clear()
        leagues.addAll(data)
        val spinnerAdapter = ArrayAdapter(ctx,
            android.R.layout.simple_spinner_item,
            data.filter { it.strSport.equals("Soccer")})

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showLoading()
                item = spinner.selectedItem as League
                Log.d("Item", item.strLeague)
                presenter.getNextMatchList(item.idLeague.toString())
            }

        }
    }

}
