/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.activity

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
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.net.SendingUtil.sendTweet
import com.nachtgeistw.impurebirdkt.net.TimelineUtil.pullHomeTimeline
import com.nachtgeistw.impurebirdkt.util.ActivityCollector.finishAll
import com.nachtgeistw.impurebirdkt.util.ImpureBirdApplication
import com.nachtgeistw.impurebirdkt.util.ReturnCode.Companion.SEND_TWEET
import com.nachtgeistw.impurebirdkt.util.TwitterErrorCode.Companion.DUPLICATE_CODE
import com.nachtgeistw.impurebirdkt.util.Util.Companion.buildTwitter
import com.nachtgeistw.impurebirdkt.util.Util.Companion.showToastShort
import kotlinx.coroutines.*
import twitter4j.*
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
    private lateinit var statusList: List<Status>
    lateinit var twitter: Twitter

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        // Twitter4j init
        twitter = buildTwitter(this)

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
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

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
        showToastShort(getString(R.string.double_click_to_exit))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    //对通过intent返回的数据做处理（这里只有一个send_tweet）
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SEND_TWEET -> if (resultCode == RESULT_OK) {
                runBlocking { sendTweet(twitter, data!!.getStringExtra("tweet_content")) }
            }
        }
    }



}