package com.app.lottoproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.util.*

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy { findViewById(R.id.clearButton) }
    private val addButton: Button by lazy { findViewById(R.id.addButton) }
    private val runButton: Button by lazy { findViewById(R.id.runButton) }
    private val numberPicker: NumberPicker by lazy { findViewById(R.id.numberPicker) }

    private var didRun = false
    private val pickNumberSet: TreeSet<Int> = TreeSet()

    private val NUMBERPICKER_MAX = 45

    private val numberTextViewList: List<TextView> by lazy {
        listOf(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = NUMBERPICKER_MAX

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            for (i in numberTextViewList.indices){
                numberTextViewList[i].isVisible = false
            }
            pickNumberSet.clear()
            didRun = false
        }
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 6){
                Toast.makeText(this, "번호는 6개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pickNumberSet.add(numberPicker.value)

            for (i in pickNumberSet.indices){
                numberTextViewList[i].apply {
                    isVisible = true
                    text = pickNumberSet.elementAt(i).toString()
                    background = textViewBackground(pickNumberSet.elementAt(i))
                }
            }
        }
    }

    private fun textViewBackground(num: Int) : Drawable? =
        when(num){
            in 1..10 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_yellow)
            in 11..20 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_blue)
            in 21..30 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_red)
            in 31..40 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_gray)
            else -> ContextCompat.getDrawable(this@MainActivity, R.drawable.circle_green)
        }

    private fun initRunButton(){
        runButton.setOnClickListener {
            if(pickNumberSet.size == 6){
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newList: TreeSet<Int> = TreeSet<Int>()
            newList.addAll(getRandomNumber())
            newList.addAll(pickNumberSet)

            for (i in 0 until newList.size){
                numberTextViewList[i].apply {
                    isVisible = true
                    text = newList.elementAt(i).toString()
                    background = textViewBackground(newList.elementAt(i))
                }
            }

            didRun = true
        }
    }

    private fun getRandomNumber(): TreeSet<Int> {
        val random = Random()
        val newRandomList: TreeSet<Int> = TreeSet()
        while (newRandomList.size < numberTextViewList.size-pickNumberSet.size){
            val num = random.nextInt(NUMBERPICKER_MAX) + 1
            if(!pickNumberSet.contains(num)) newRandomList.add(num)
        }

        return newRandomList
    }
}