package com.example.diapertracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
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

        //check onSaveInstanceState
        if(savedInstanceState != null){
            diaperChangesText.text = savedInstanceState.getString("diaperChanges")
            diaperChangesCount.text = savedInstanceState.getString("diaperCount")
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("diaperChanges", diaperChangesText.text.toString())
        outState.putString("diaperCount", diaperChangesCount.text.toString())
    }

    //Create a new diaper to add to a list
    private fun addNewDiaper(){
        //Get the current time
        var timeOfChange = currentTime.text.toString()
        if(timeOfChange == ""){
            timeOfChange = "00:00"
        }

        var newDiaper = ""

        newDiaper = when{
            dirtyButton.isChecked -> {
                "- A dirty diaper was changed at $timeOfChange"
            }
            wetButton.isChecked -> {
                "- A wet diaper was changed at $timeOfChange"
            }
            else -> {
                "-A dry diaper was changed at $timeOfChange"
            }
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

        //Clear the edit text
        currentTime.setText("")

        //Hide the keyboard
        hideKeyboard()
    }

    //Clear all entered diapers from our list
    private fun clear() {
        //reset all UI and counter
        diaperCount = 0
        diaperChangesText.text = ""
        diaperChangesCount.text = ""

    }

    //Hide keyboard
    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentTime.windowToken, 0)
    }
}