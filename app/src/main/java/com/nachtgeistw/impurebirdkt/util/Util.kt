package com.nachtgeistw.impurebirdkt.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

open class Util {
    companion object {
        fun showToastLong(string: String) {
            Toast.makeText(ImpureBirdApplication.context, string, Toast.LENGTH_LONG).show()
        }

        fun showToastShort(string: String) {
            Toast.makeText(ImpureBirdApplication.context, string, Toast.LENGTH_SHORT).show()
        }

        fun buildTwitter(context: Context): Twitter {
            Log.i("Twitter", "Util > buildTwitter")
            val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val builder = ConfigurationBuilder()
            builder.setOAuthConsumerKey(ImpureBirdApplication.CONSUMER_KEY)
            builder.setOAuthConsumerSecret(ImpureBirdApplication.CONSUMER_SECRET_KEY)
            val token: String? = sharedPreferences.getString("token", "")
            val tokenSecret: String? = sharedPreferences.getString("token_secret", "")
            val accessToken = AccessToken(token, tokenSecret)
            return TwitterFactory(builder.build()).getInstance(accessToken)
        }

        //Auto adjust width and height
        fun imageViewSelfAdaption(pic1: ImageView, maxHeightScale: Int = 5) {
            val width: Int = Resources.getSystem().displayMetrics.widthPixels
            val screenWidth = (width * 0.9).toInt()
            val lp: ViewGroup.LayoutParams = pic1.layoutParams
            lp.width = screenWidth
            lp.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            pic1.layoutParams = lp
            pic1.maxWidth = screenWidth
            pic1.maxHeight = screenWidth * maxHeightScale
        }
    }
}