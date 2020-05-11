package com.nachtgeistw.impurebirdkt.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.activity.HomepageActivity
import com.nachtgeistw.impurebirdkt.databinding.FragmentHomeBinding
import com.nachtgeistw.impurebirdkt.net.TimelineUtil
import com.nachtgeistw.impurebirdkt.ui.TweetAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import twitter4j.Status
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    private val homeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private lateinit var statusList: List<Status>
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var binding: FragmentHomeBinding

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(
            "Twitter",
            "${javaClass.simpleName} > onCreate"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(
            "Twitter",
            "${javaClass.simpleName} > onCreateView"
        )

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        binding = FragmentHomeBinding.inflate(inflater)
        /*
        下一句应该放到Adapter去吧……需要position什么的
        https://www.jianshu.com/p/8421aa03fc14
        homeViewModel.userName.observe(
            viewLifecycleOwner,
            Observer { homepage_tweet_user_name.text = statusList[1].user.name })
        */
        //设置recyclerView
        recyclerView = root.findViewById(R.id.home_timeline_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        //设置下拉刷新
        swipeRefreshLayout = root.findViewById(R.id.home_timeline_swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            pullDownRefreshSwipe()
        }

        // pull home timeline
        pullDownRefresh()

        return root
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun pullDownRefreshSwipe() {
        launch {
            statusList =
                TimelineUtil.pullHomeTimeline(HomepageActivity::twitter.call(activity))
            Log.i(
                "Twitter",
                "pullDownRefreshSwipe > is statusList empty? > ${statusList.isEmpty()}"
            )
            recyclerView.adapter = TweetAdapter(statusList)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun pullDownRefresh() {
        launch {
            statusList =
                TimelineUtil.pullHomeTimeline(HomepageActivity::twitter.call(activity))
            Log.i("Twitter", "pullDownRefresh > is statusList empty? > ${statusList.isEmpty()}")
            recyclerView.adapter = TweetAdapter(statusList)
        }
    }
}
