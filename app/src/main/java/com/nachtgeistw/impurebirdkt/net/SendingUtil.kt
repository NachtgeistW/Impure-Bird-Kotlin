/*
 * Copyright (c) 2020/5/8 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 * Manager the function of Sending(eg, Send tweet and direct message)
 */

package com.nachtgeistw.impurebirdkt.net

import android.util.Log
import com.nachtgeistw.impurebirdkt.activity.HomepageActivity
import com.nachtgeistw.impurebirdkt.util.TwitterErrorCode
import com.nachtgeistw.impurebirdkt.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.TwitterException

object SendingUtil {
    // Send tweet
    suspend fun sendTweet(twitter: Twitter, tweetContent: String?) {
        var resultCode = 0
        coroutineScope {
            // Update status runs
            launch {
                withContext(Dispatchers.Default) {
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
                    0 -> Util.showToastShort("Tweet sent.")
                    TwitterErrorCode.DUPLICATE_CODE -> Util.showToastShort("Duplicate tweet.")
                    else -> Util.showToastShort("Tweet sent failed.")
                }
            }
        }
    }
}