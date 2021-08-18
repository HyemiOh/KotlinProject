package com.app.secretdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryEditText = findViewById<EditText>(R.id.diaryEditText)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit{
                putString("detail", diaryEditText.text.toString())
            }

            Log.d("Save!!", "diaryEditText=${diaryEditText.text.toString()}")

        }

        diaryEditText.addTextChangedListener {

            Log.d("DiaryAcitivity", "diaryEditText=${diaryEditText.text.toString()}")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)

        }
    }
}