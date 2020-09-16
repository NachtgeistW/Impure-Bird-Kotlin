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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nachtgeistw.impurebirdkt.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CallBackActivity : AppCompatActivity(), CoroutineScope {

    // Preparing for using coroutine
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_back)

        val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        var isLogin = false

        val uri = intent.data
        if (uri != null && uri.toString().startsWith("callback://CallBackActivity")) {
            launch {
                val mToken = async(context = Dispatchers.IO) {
                    //asyncは値を返せる
                    val verify = uri.getQueryParameter("oauth_verifier")
                    return@async MainActivity.mOauth.getOAuthAccessToken(
                        MainActivity.mRequest,
                        verify
                    )
                }.await()
                if (mToken != null) {
                    isLogin = true
                    editor.putString("token", mToken.token)
                    editor.putString("token_secret", mToken.tokenSecret)
                }
                editor.putBoolean("is_login", isLogin)
                editor.apply()

                if (isLogin) {
                    homepageActivityStart(this@CallBackActivity)
                    finish()
                } else {
                    mainActivityStart(this@CallBackActivity)
                    finish()
                }
            }
        }
    }

    companion object {
        fun mainActivityStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun homepageActivityStart(context: Context) {
            val intent = Intent(context, HomepageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
