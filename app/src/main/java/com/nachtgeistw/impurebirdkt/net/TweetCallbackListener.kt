/*
 * Copyright (c) 2020/5/7 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.net

import android.util.Log
import twitter4j.TwitterException

interface TweetCallbackListener<T> {
    fun onSucceed()
    fun onError(e: TwitterException) {
        Log.e("Twitter", "${e.exceptionCode}: ${e.errorMessage}")
        e.printStackTrace()
    }
}