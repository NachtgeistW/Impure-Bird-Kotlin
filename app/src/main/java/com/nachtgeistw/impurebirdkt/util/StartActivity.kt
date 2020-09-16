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
import com.nachtgeistw.impurebirdkt.activity.PicActivity
import com.nachtgeistw.impurebirdkt.activity.SendTweetActivity

interface StartActivity {
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

        fun picActivityStart(url: Array<String>){
            val intent = Intent(ImpureBirdApplication.context, PicActivity::class.java)
            intent.putExtra("url", url)
            ImpureBirdApplication.context.startActivity(intent)
        }
    }
}