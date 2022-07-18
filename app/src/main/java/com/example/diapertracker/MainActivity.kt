package com.example.diapertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    //Initialize views with lateinit
    private lateinit var dirtyButton: RadioButton
    private lateinit var wetButton: RadioButton
    private lateinit var dryButton: RadioButton
    private lateinit var currentTime: EditText
    private lateinit var diaperChangesText: TextView
    private lateinit var diaperChangesCount: TextView

    //Counter variable
    private var diaperCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get a reference to our buttons and set listeners
        val addButton: Button = findViewById(R.id.main_bt_add)
        val clearButton: Button = findViewById(R.id.main_bt_clear)

        addButton.setOnClickListener { addNewDiaper() }
        clearButton.setOnClickListener { clear() }

        //Set values to other views
        dirtyButton = findViewById(R.id.main_rb_dirty)
        wetButton = findViewById(R.id.main_rb_wet)
        dryButton = findViewById(R.id.main_rb_dry)
        currentTime = findViewById(R.id.main_et_time)
        diaperChangesText = findViewById(R.id.main_tv_diaper_changes)
        diaperChangesCount = findViewById(R.id.main_tv_diaper_count)
    }

    //Create a new diaper to add to a list
    private fun addNewDiaper(){
        //Get the current time
        val timeOfChange = currentTime.text.toString()

        var newDiaper = ""

        if(dirtyButton.isChecked){
            //Log.i("test", "Dirty Diaper Changed")
            newDiaper = "- A dirty diaper was changed at $timeOfChange"
        } else if(wetButton.isChecked){
            //Log.i("test", "Wet")
            newDiaper = "- A wet diaper was changed at $timeOfChange"
        } else {
            //Log.i("test", "dry")
            newDiaper = "- A dry diaper was changed at $timeOfChange"
        }

        //increment diaper counter
        diaperCount++

        //Update our diaper list
        updateDiaperList(newDiaper)
    }

    //Add the new diaper to our list
    private fun updateDiaperList(newDiaper: String){
        //Get the old list of diapers and add the new one to the end
        val oldDiapers = diaperChangesText.text.toString()
        val updatedDiapers = "$oldDiapers \n$newDiaper"

        //Update the UI text
        diaperChangesText.text = updatedDiapers

        diaperChangesCount.text = "$diaperCount total diapers changed."
    }

    //Clear all entered diapers from our list
    private fun clear() {

    }
}