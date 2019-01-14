package com.yayanheryanto.footballmatchschedule.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.gson.Gson
import com.yayanheryanto.footballmatchschedule.R
import com.yayanheryanto.footballmatchschedule.adapter.PlayerAdapter
import com.yayanheryanto.footballmatchschedule.api.ApiRepository
import com.yayanheryanto.footballmatchschedule.model.Player
import com.yayanheryanto.footballmatchschedule.model.Team
import com.yayanheryanto.footballmatchschedule.presenter.PlayerPresenter
import com.yayanheryanto.footballmatchschedule.ui.activity.DetailPlayerActivity
import com.yayanheryanto.footballmatchschedule.util.invisible
import com.yayanheryanto.footballmatchschedule.util.visible
import com.yayanheryanto.footballmatchschedule.view.PlayerView
import kotlinx.android.synthetic.main.fragment_player.*
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
class PlayerFragment : Fragment(), PlayerView {

    private var teams: Team? = null
    private val players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var listPlayer: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var progressBar: ProgressBar

    companion object {
        private const val DATA_TEAM = "data_team"
        fun newInstance(team: Team): PlayerFragment {
            val fragment = PlayerFragment()
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
        val rootView = inflater.inflate(R.layout.fragment_player, container, false)
        progressBar = rootView.find(R.id.progressBar) as ProgressBar


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            teams = arguments!!.getParcelable(DATA_TEAM)

            rvPlayer.layoutManager = LinearLayoutManager(ctx)
            adapter = PlayerAdapter(ctx, players){
                startActivity<DetailPlayerActivity>("player" to it)
            }
            rvPlayer.adapter = adapter

            val request = ApiRepository()
            val gson = Gson()
            presenter = PlayerPresenter(this@PlayerFragment, request, gson)
            presenter.getAllPlayer(teams?.teamId)

        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showAllPlayer(data: List<Player>) {
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
