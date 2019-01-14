package com.yayanheryanto.footballmatchschedule.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.widget.ProgressBar
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.MatchAdapter
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.model.Match
import com.yayanheryanto.footballmatchschedule.presenter.SearchMatchPresenter
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.SearchMatchView
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        progressBar = find(R.id.progressBar) as ProgressBar
        listTeam = find(R.id.rvSearchMatch) as RecyclerView

        listTeam.layoutManager = LinearLayoutManager(ctx)
        adapter = MatchAdapter(ctx, match){
            startActivity<DetailMatchActivity>("matchId" to "${it.matchId}",
                "idHomeTeam" to "${it.idHomeTeam}",
                "idAwayTeam" to "${it.idAwayTeam}")
        }
        listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this@SearchMatchActivity, request, gson)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_match, menu)
        val searchMenu = menu?.findItem(R.id.action_search_match)
        val searchView = searchMenu?.actionView as SearchView?
        searchView?.queryHint = "Search Match"
        searchView?.isIconified = false
        searchMenu?.expandActionView()
        searchView?.setQuery("",false)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val query: CharSequence = newText.toString()
                if (TextUtils.getTrimmedLength(query) > 0) {
                    val teamName = query.toString().replace(" ", "%20")
                    presenter.getSearchMatch(teamName)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        match.clear()
        match.addAll(data.filter { !it.matchTime.equals(null) && it.homeScore.equals(null)})
        adapter.notifyDataSetChanged()
    }

}
