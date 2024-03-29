package com.example.myapplicationkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.handler.AsyncTaskActivity
import com.example.myapplication.handler.MyHandlerActivity
import com.example.myapplication.lottie.LottieAnimationActivity
import com.example.myapplication.permission.PermissionActivity
import com.example.myapplicationkotlin.picture.CamaraActivity
import com.example.myapplicationkotlin.service.MyServiceActivity
import com.example.myapplicationkotlin.view.MyViewActivity
import com.example.myapplicationkotlin.webview.WebViewActivity
import com.example.myapplicationkotlin.wifitest.WifiTestActivity
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.*

const val EXTRA_MESSAGE = "com.example.myapplicationkotlin.MESSAGE"
const val NAME = "com.example.myapplicationkotlin.NAME"
const val AGE = "com.example.myapplicationkotlin.AGE"
const val PERSON = "com.example.myapplicationkotlin.PERSON"
const val GAME_STATE_KEY = "game.state.key"
var gameState: String? = null

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameState = savedInstanceState?.getString(GAME_STATE_KEY)
        Log.i(TAG, "mainActivity create")

        findViewById<Button>(R.id.handler_view).setOnClickListener {
            val intent = Intent(this, MyHandlerActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.async_task_view).setOnClickListener {
            val intent = Intent(this, AsyncTaskActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.lottie).setOnClickListener {
            val intent = Intent(this, LottieAnimationActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.permission).setOnClickListener {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.camara_view).setOnClickListener {
//            val intent = Intent(this, CamaraActivity::class.java)
//            startActivity(intent)
            io.reactivex.Observable.fromCallable{
                null
            }.subscribeOn(Schedulers.io())
                .subscribe({
                   Log.i("TAG", "true")
                },{
                    it.printStackTrace()
                })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "mainActivity start ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "mainActivity resume ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "mainActivity pause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "mainActivity stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "mainActivity destroy")
    }

    fun intoInputActivity(view: View) {
        val intent = Intent(this, InputActivity::class.java)
//        startActivityForResult(intent, 1) //该方法已被弃用
        Log.i(TAG, "start inputActivity from mainActivity")
        resultLauncher.launch(intent)
    }

    fun intoBaseWidgetActivity(view: View) {
        val intent = Intent(this, BaseWidgetActivity::class.java)
        Log.i(TAG, "start BaseWidgetActivity from mainActivity")
        startActivity(intent)
    }

    fun intoConnectionFailedActivity(view: View) {
        val intent = Intent(this, ConnectionFailedActivity::class.java)
        Log.i(TAG, "start ConnectionActivity from mainActivity")
        startActivity(intent)
    }

    fun intoCreateAccessCodeActivity(view: View) {
        val intent = Intent(this, CreateAccessCodeActivity::class.java)
        Log.i(TAG, "start CreateAccessCodeActivity from mainActivity")
        startActivity(intent)
    }

    fun intoAccessActivity(view: View) {
        val intent = Intent(this, AccessActivity::class.java)
        Log.i(TAG, "start AccessActivity from mainActivity")
        startActivity(intent)
    }

    fun intoWifiTestActivity(view: View) {
        val intent = Intent(this, WifiTestActivity::class.java)
        Log.i(TAG, "start WifiTestActivity from mainActivity ")
        startActivity(intent)
    }

    fun intoWebViewActivity(view: View) {
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
    }

    fun intoMyViewActivity(view: View) {
        val intent = Intent(this, MyViewActivity::class.java)
        startActivity(intent)
    }

    fun intoMyServiceActivity(view: View) {
        val intent = Intent(this, MyServiceActivity::class.java)
        startActivity(intent)
    }

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val person = data?.extras?.get(PERSON) as Person
                val name = person.name
                val age = person.age
//                val name = data?.getStringExtra(NAME)
//                val age = data?.getStringExtra(AGE)
                val nameTextView = findViewById<TextView>(R.id.name).setText(name)
                val ageTextView = findViewById<TextView>(R.id.age).setText(age)
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        val name = findViewById<TextView>(R.id.name).text.toString()
        val age = findViewById<TextView>(R.id.age).text.toString()
        outState.run {
            putString(GAME_STATE_KEY, gameState)
            putString(NAME, name)
            putString(AGE, age)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        findViewById<TextView>(R.id.name).setText(savedInstanceState.getString(NAME))
        findViewById<TextView>(R.id.age).setText(savedInstanceState.getString(AGE))

        // super.onRestoreInstanceState(savedInstanceState)
    }

}
// 移除自带的顶部导航栏