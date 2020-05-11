package com.nachtgeistw.impurebirdkt.util

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nachtgeistw.impurebirdkt.activity.HomepageActivity
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import kotlin.String

open class Util {
    companion object {
        fun showToastLong(string: String) {
            Toast.makeText(ImpureBirdApplication.context, string, Toast.LENGTH_LONG).show()
        }

        fun showToastShort(string: String) {
            Toast.makeText(ImpureBirdApplication.context, string, Toast.LENGTH_SHORT).show()
        }

        fun buildTwitter(context: Context): Twitter {
            val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val builder = ConfigurationBuilder()
            builder.setOAuthConsumerKey(ImpureBirdApplication.CONSUMER_KEY)
            builder.setOAuthConsumerSecret(ImpureBirdApplication.CONSUMER_SECRET_KEY)
            val token: String? = sharedPreferences.getString("token", "")
            val tokenSecret: String? = sharedPreferences.getString("token_secret", "")
            val accessToken = AccessToken(token, tokenSecret)
            return TwitterFactory(builder.build()).getInstance(accessToken)
        }
    }
}