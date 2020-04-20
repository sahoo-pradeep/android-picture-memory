package com.projects.pradeep.picturememory

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.projects.pradeep.picturememory.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding
    private var oneOpen: Boolean = false
    private var totalOpen: Int = 0
    private var openMap: HashMap<Int, Boolean> = HashMap()
    private var colorMap: HashMap<Int, Int> = HashMap()
    private var tempOpen: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setDefault()
        setColorMap()
        setListeners()

        bindings.resetButton.setOnClickListener {
            setDefault()
        }
    }

    private fun setColorMap() {
        colorMap.put(red_1_1.id, android.R.color.holo_red_dark)
        colorMap.put(red_2_3.id, android.R.color.holo_red_dark)
        colorMap.put(green_1_3.id, android.R.color.holo_green_dark)
        colorMap.put(green_2_2.id, android.R.color.holo_green_dark)
        colorMap.put(blue_1_2.id, android.R.color.holo_blue_dark)
        colorMap.put(blue_2_1.id, android.R.color.holo_blue_dark)
    }

    fun setDefault() {
        openMap.put(red_1_1.id, false)
        openMap.put(blue_1_2.id, false)
        openMap.put(green_1_3.id, false)
        openMap.put(blue_2_1.id, false)
        openMap.put(green_2_2.id, false)
        openMap.put(red_2_3.id, false)

        red_1_1.setBackgroundColor(Color.LTGRAY)
        red_2_3.setBackgroundColor(Color.LTGRAY)
        green_1_3.setBackgroundColor(Color.LTGRAY)
        green_2_2.setBackgroundColor(Color.LTGRAY)
        blue_1_2.setBackgroundColor(Color.LTGRAY)
        blue_2_1.setBackgroundColor(Color.LTGRAY)

        oneOpen = false
        totalOpen = 0
        tempOpen = null

    }

    private fun setListeners() {
        val clickableViews: List<View> =
            listOf(
                red_1_1,
                blue_1_2,
                green_1_3,
                blue_2_1,
                green_2_2,
                red_2_3
            )

        for (item in clickableViews) {
            item.setOnClickListener {
                onBoxClick(it)
            }
        }
    }

    private fun onBoxClick(view: View) {
        // If already open, do nothing
        if (openMap.get(view.id)!!) {
            return
        }

        // Change color
        view.setBackgroundResource(colorMap.get(view.id)!!)
        oneOpen = !oneOpen

        // If one is open, store in tempOpen
        if (oneOpen) {
            openMap.put(view.id, true)
            tempOpen = view
            totalOpen++
            return
        }

        when (view) {
            red_1_1 -> validate(red_1_1, red_2_3)
            red_2_3 -> validate(red_2_3, red_1_1)
            green_1_3 -> validate(green_1_3, green_2_2)
            green_2_2 -> validate(green_2_2, green_1_3)
            blue_1_2 -> validate(blue_1_2, blue_2_1)
            blue_2_1 -> validate(blue_2_1, blue_1_2)
        }

    }

    private fun validate(currentView: View, validateView: View) {
        if (openMap.get(validateView.id)!!) {
            tempOpen = null
            totalOpen++
        } else {
            Handler().postDelayed({
                tempOpen?.setBackgroundColor(Color.LTGRAY)
                currentView.setBackgroundColor(Color.LTGRAY)
                openMap.put(tempOpen?.id!!, false)
                openMap.put(currentView?.id!!, false)
                totalOpen -= 2
            }, 250)
        }
    }
}
