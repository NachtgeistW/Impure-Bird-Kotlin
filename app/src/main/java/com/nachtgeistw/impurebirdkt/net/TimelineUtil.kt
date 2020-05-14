/*
 * Copyright (c) 2020/5/7 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 * Define function about timeline, eg: user timeline and homepage timeline
 */

package com.nachtgeistw.impurebirdkt.net

import android.util.Log
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.util.ImpureBirdApplication.Companion.context
import com.nachtgeistw.impurebirdkt.util.Util.Companion.showToastLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import twitter4j.Paging
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterException
import java.lang.Exception

object TimelineUtil {
    suspend fun pullHomeTimeline(twitter: Twitter): List<Status> {
        lateinit var statusList: List<Status>
        try {
            withContext(Dispatchers.IO) {
                Log.i(
                    "Twitter",
                    "TimelineUtil > pullHomeTimeline"
                )
                statusList = twitter.getHomeTimeline(Paging(1, 100))
            }
        } catch (e: TwitterException) {
            showToastLong(context.getString(R.string.pull_home_timeline_failed))
            Log.e("Twitter", "${e.exceptionCode}: ${e.errorMessage}")
            e.printStackTrace()
        } catch (e: Exception) {

        }

        return statusList
    }
}