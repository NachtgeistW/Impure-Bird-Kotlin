package com.nachtgeistw.impurebirdkt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nachtgeistw.impurebirdkt.util.BaseActivity
import com.nachtgeistw.impurebirdkt.util.Key.Companion.CONSUMER_KEY
import com.nachtgeistw.impurebirdkt.util.Key.Companion.CONSUMER_SECRET_KEY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import twitter4j.auth.OAuthAuthorization
import twitter4j.auth.RequestToken
import twitter4j.conf.ConfigurationContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {


    //コルーチンを使うための準備
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    //認証オブジェクトの作成
    companion object {
        val mOauth = OAuthAuthorization(ConfigurationContext.getInstance())
        lateinit var mRequest: RequestToken
        var sharedPreferences: SharedPreferences? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        if (isTwitterLoginInAlready())
            login_button.setOnClickListener {
                Log.i("login", "if > false")
                onClick()
            }
        else {
            Log.i("login", "if > true")
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun onClick() {
        launch {
            mOauth.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET_KEY)
            mOauth.oAuthAccessToken = null
            var uri: Uri?

            async(context = Dispatchers.IO) {
                mRequest = mOauth.getOAuthRequestToken(
                    "callback://CallBackActivity"
                )    //コールバックの設定

                val intent = Intent(Intent.ACTION_VIEW)
                uri = Uri.parse(mRequest.authenticationURL)
                intent.data = uri
                startActivityForResult(intent, 0)            //暗黙インテントでWebブラウザを呼び出して認証
            }                                                //認証情報を受け取るためにstartActivityForResult()
        }
    }

    // if sharedPreferences equals to null, or it doesn't contain "is_login", or it's false
    // return false. Else, return true
    private fun isTwitterLoginInAlready(): Boolean {
        return !(sharedPreferences == null ||
                sharedPreferences!!.contains("is_login") ||
                sharedPreferences!!.getBoolean("is_login", false))
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
