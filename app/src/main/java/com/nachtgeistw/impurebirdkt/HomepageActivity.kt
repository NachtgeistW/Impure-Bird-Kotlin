/*
 * Copyright (c) 2020/5/4 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.nachtgeistw.impurebirdkt.util.ActivityCollector.finishAll
import com.nachtgeistw.impurebirdkt.util.Key
import com.nachtgeistw.impurebirdkt.util.Key.Companion.TWEET_CONTENT
import com.nachtgeistw.impurebirdkt.util.ReturnCode.Companion.SEND_TWEET
import com.nachtgeistw.impurebirdkt.util.TwitterErrorCode.Companion.DUPLICATE_CODE
import com.nachtgeistw.impurebirdkt.util.Util.Companion.showToastShort
import kotlinx.coroutines.*
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

class HomepageActivity : AppCompatActivity(), CoroutineScope {

    // Preparing for using coroutine
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // Preparing for using Twitter4j
    private lateinit var twitter: Twitter

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, SendTweetActivity::class.java)
            startActivityForResult(intent, SEND_TWEET)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Twitter4j init
        twitter = buildTwitter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.homepage, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // double click to exit
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        Log.d("test", doubleBackToExitPressedOnce.toString())
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAll()
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showToastShort(this, getString(R.string.double_click_to_exit))
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    //对通过intent返回的数据做处理（这里只有一个send_tweet）
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SEND_TWEET -> if (resultCode == RESULT_OK) {
                sendTweet(this, twitter, data!!.getStringExtra(TWEET_CONTENT))
            }
        }
    }

    // Send tweet
    private fun sendTweet(activity: HomepageActivity, twitter: Twitter, tweetContent: String?) {
        var resultCode = 0
        // Update status runs
        activity.launch {
            withContext(Dispatchers.Default) {
                Log.i(
                    "Twitter",
                    "${javaClass.simpleName} > SendTweetActivity > doInBackground > true"
                )
                try {
                    twitter.updateStatus(tweetContent)
                } catch (e: TwitterException) {
                    Log.i("Twitter", "${e.errorCode}: ${e.errorMessage}")
                    resultCode = e.errorCode
                    e.printStackTrace()
                }
            }
            // Show result
            when (resultCode) {
                0 -> showToastShort(activity, "Tweet sent.")
                DUPLICATE_CODE -> showToastShort(activity, "Duplicate tweet.")
                else -> showToastShort(activity, "Tweet sent failed.")
            }
        }

    }

    companion object {
        fun buildTwitter(activity: HomepageActivity): Twitter {
            val sharedPreferences = activity.getSharedPreferences("data", Context.MODE_PRIVATE)
            val builder = ConfigurationBuilder()
            builder.setOAuthConsumerKey(Key.CONSUMER_KEY)
            builder.setOAuthConsumerSecret(Key.CONSUMER_SECRET_KEY)
            val token: String? = sharedPreferences.getString("token", "")
            val tokenSecret: String? = sharedPreferences.getString("token_secret", "")
            val accessToken = AccessToken(token, tokenSecret)
            return TwitterFactory(builder.build()).getInstance(accessToken)
        }
    }
}