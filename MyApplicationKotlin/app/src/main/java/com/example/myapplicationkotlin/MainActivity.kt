package com.example.myapplicationkotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

const val EXTRA_MESSAGE = "com.example.myapplicationkotlin.MESSAGE"
const val NAME = "com.example.myapplicationkotlin.NAME"
const val AGE = "com.example.myapplicationkotlin.AGE"
const val GAME_STATE_KEY = "game.state.key"
var gameState: String? = null

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameState = savedInstanceState?.getString(GAME_STATE_KEY)
        Log.i(TAG, "mainActivity create")

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "mainActivity start")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "mainActivity resume")
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

    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun intoInputActivity(view: View) {
        val intent = Intent(this, InputActivity::class.java)
//        startActivityForResult(intent, 1) //该方法已被弃用
        Log.i(TAG, "start inputActivity from mainActivity")
        resultLauncher.launch(intent)
    }

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val name = data?.getStringExtra(NAME)
                val age = data?.getStringExtra(AGE)
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