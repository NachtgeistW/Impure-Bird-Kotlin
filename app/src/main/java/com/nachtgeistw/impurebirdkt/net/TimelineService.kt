/*
 * Copyright (c) 2020/5/7 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.net

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.nachtgeistw.impurebirdkt.util.ImpureBirdApplication
import com.nachtgeistw.impurebirdkt.util.Util.Companion.buildTwitter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import twitter4j.Twitter

class TimelineService : Service() {
    private lateinit var twitter: Twitter

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Twitter", "TimelineService > onStartCommand")
        runBlocking { TimelineUtil.pullHomeTimeline(twitter) }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Twitter", "TimelineService > onStartCommand")
        twitter = buildTwitter(ImpureBirdApplication.context)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}