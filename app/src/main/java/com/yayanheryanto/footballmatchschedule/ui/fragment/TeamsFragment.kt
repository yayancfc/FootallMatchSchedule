package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.TeamAdapter
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.model.League
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.presenter.TeamPresenter
import com.yayanheryanto.footballmatchschedule.ui.activity.DetailTeamActivity
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.TeamView
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
class TeamsFragment : Fragment(), TeamView {

    private val team: MutableList<Team> = mutableListOf()
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: TeamAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var item: League
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_teams, container, false)

        setHasOptionsMenu(true)
        swipeRefresh = rootView.find(R.id.swipeRefresh) as SwipeRefreshLayout
        progressBar = rootView.find(R.id.progressBar) as ProgressBar
        listTeam = rootView.find(R.id.rvListTeam) as RecyclerView
        spinner = rootView.find(R.id.spinner_list_league) as Spinner

        listTeam.layoutManager = LinearLayoutManager(ctx)

        listTeam.layoutManager = LinearLayoutManager(ctx)
        adapter = TeamAdapter(ctx, team) {
            startActivity<DetailTeamActivity>("teamId" to "${it.teamId}")
        }

        listTeam.adapter = adapter

        spinner = rootView.find(R.id.spinner_list_league) as Spinner

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this@TeamsFragment, request, gson)
        presenter.getAllLeague()
        swipeRefresh.setOnRefreshListener {
            val leagueName = item.strLeague.toString().replace(" ", "%20")
            presenter.getTeamLeague(leagueName)
        }

        return rootView
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueList(data: List<League>) {
        leagues.clear()
        leagues.addAll(data)
        val spinnerAdapter = ArrayAdapter(ctx,
            android.R.layout.simple_spinner_item,
            data.filter { it.strSport.equals("Soccer") })

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showLoading()
                item = spinner.selectedItem as League
                Log.d("Item", item.strLeague)
                val leagueName = item.strLeague.toString().replace(" ", "%20")
                presenter.getTeamLeague(leagueName)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)
        val searchMenu = menu?.findItem(R.id.action_search)
        val searchView = searchMenu?.actionView as SearchView?
        searchView?.queryHint = "Search Team"
        searchView?.setIconifiedByDefault(true)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query: CharSequence = newText.toString()
                if (TextUtils.getTrimmedLength(query) > 0) {
                    spinner.invisible()
                    val teamName = query.toString().replace(" ", "%20")
                    presenter.getTeamSearch(teamName)
                } else {
                    spinner.visible()
                }

                return true
            }

        })
    }

    override fun showTeamLeague(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        team.clear()
        team.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showTeamSearch(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        team.clear()
        team.addAll(data)
        adapter.notifyDataSetChanged()

    }

}
