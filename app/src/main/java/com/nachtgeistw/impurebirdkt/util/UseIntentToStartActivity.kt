/*
 * Copyright (c) 2020/5/2 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.util

import android.content.Context
import android.content.Intent
import com.nachtgeistw.impurebirdkt.HomepageActivity
import com.nachtgeistw.impurebirdkt.MainActivity
import com.nachtgeistw.impurebirdkt.SendTweetActivity

interface UseIntentToStartActivity {
    companion object{
        fun mainActivityStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun homepageActivityStart(context: Context) {
            val intent = Intent(context, HomepageActivity::class.java)
            context.startActivity(intent)
        }

        fun sendTweetActivityStart(context: Context) {
            val intent = Intent(context, SendTweetActivity::class.java)
            context.startActivity(intent)
        }
    }
}