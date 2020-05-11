/*
 * Copyright (c) 2020/5/2 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.util

import android.content.Intent
import com.nachtgeistw.impurebirdkt.activity.HomepageActivity
import com.nachtgeistw.impurebirdkt.activity.MainActivity
import com.nachtgeistw.impurebirdkt.activity.SendTweetActivity

interface UseIntentToStartActivity {
    companion object{
        fun mainActivityStart() {
            val intent = Intent(ImpureBirdApplication.context, MainActivity::class.java)
            ImpureBirdApplication.context.startActivity(intent)
        }

        fun homepageActivityStart() {
            val intent = Intent(ImpureBirdApplication.context, HomepageActivity::class.java)
            ImpureBirdApplication.context.startActivity(intent)
        }

        fun sendTweetActivityStart() {
            val intent = Intent(ImpureBirdApplication.context, SendTweetActivity::class.java)
            ImpureBirdApplication.context.startActivity(intent)
        }
    }
}