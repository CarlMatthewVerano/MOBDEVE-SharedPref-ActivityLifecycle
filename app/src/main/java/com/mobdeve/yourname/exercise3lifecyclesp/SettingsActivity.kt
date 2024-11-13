package com.mobdeve.yourname.exercise3lifecyclesp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    // Views for the switches
    private lateinit var linearViewSwitch: Switch
    private lateinit var hideLikeSwitch: Switch

    // Indicators for what Layout should be used or if the like buttons should be hidden
    private val viewSelected = LayoutType.LINEAR_VIEW_TYPE.ordinal // int of LayoutType.LINEAR_VIEW_TYPE (default) or LayoutType.GRID_VIEW_TYPE
    private val hideLikeSelected = false // true -> hidden buttons; false -> shown buttons (default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Instantiation of the Switch views
        this.linearViewSwitch = findViewById(R.id.viewSwitch)
        this.hideLikeSwitch = findViewById(R.id.hideLikeSwitch)

        Log.d("ON CREATE: ", returnBoolean(viewSelected).toString())
        this.linearViewSwitch.isChecked = returnBoolean(viewSelected)

        // DEBUGGING
        this.linearViewSwitch.setOnClickListener(){
            Log.d("ON CLICK: ", this.linearViewSwitch.isChecked.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        val sp : SharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val editor = sp.edit()
        Log.d("ON PAUSE VALUE:",this.returnInt(linearViewSwitch.isChecked).toString() )
        Log.d("ON PAUSE BOOL VALUE:", this.hideLikeSwitch.isChecked.toString() )
        editor.putInt("KEY_LAYOUT", this.returnInt(linearViewSwitch.isChecked))
        editor.putBoolean("KEY_BOOL_LINEAR_SWITCH", this.linearViewSwitch.isChecked)
        editor.putBoolean("KEY_BOOL_LIKE", this.hideLikeSwitch.isChecked)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sp : SharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE)
        this.linearViewSwitch.isChecked = sp.getBoolean("KEY_BOOL_LINEAR_SWITCH", returnBoolean(viewSelected))
        this.hideLikeSwitch.isChecked = sp.getBoolean("KEY_BOOL_LIKE", this.hideLikeSelected)
        Log.d("ON RESUME VALUE:",linearViewSwitch.isChecked.toString())
        Log.d("ON RESUME BOOL VALUE:", this.hideLikeSwitch.isChecked.toString() )
    }

    /*
     * Method to map an integer to the appropriate boolean value based on the given LayoutType.
     * */
    private fun returnBoolean(value: Int): Boolean {
        if (value == LayoutType.LINEAR_VIEW_TYPE.ordinal)
            return true
        else
            return false
    }

    /*
     * Method to map a boolean value (representing whether the linearViewSwitch is checked or not)
     * to appropriate LayoutType in ordinal representation.
     * */
    private fun returnInt(value: Boolean): Int {
        if (value == true)
            return LayoutType.LINEAR_VIEW_TYPE.ordinal
        else
            return LayoutType.GRID_VIEW_TYPE.ordinal
    }
}