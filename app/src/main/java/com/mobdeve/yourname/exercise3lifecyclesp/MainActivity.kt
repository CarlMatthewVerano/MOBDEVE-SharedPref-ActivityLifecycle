package com.mobdeve.yourname.exercise3lifecyclesp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Data for the application
    private lateinit var data: ArrayList<PostModel>

    // RecyclerView components
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    // Indicators for what Layout should be used or if the like buttons should be hidden
    private val recyclerViewDefaultView = LayoutType.LINEAR_VIEW_TYPE.ordinal // int of LayoutType.LINEAR_VIEW_TYPE (default) or LayoutType.GRID_VIEW_TYPE
    private val hideLikeButtons = false // true = hide buttons; false = shown buttons (default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the data, recyclerView and adapter
        this.data = DataHelper.initializeData()
        this.recyclerView = findViewById(R.id.recyclerView)
        this.myAdapter = MyAdapter(this.data)

        // Set the layout manager according to the default view
        this.recyclerView.layoutManager = getLayoutManager(this.recyclerViewDefaultView)

        // Initialize the view type and hide like button settings
        this.myAdapter.setViewType(this.recyclerViewDefaultView)
        this.myAdapter.setHideLikeBtn(this.hideLikeButtons)

        // Sets the adapter of the recycler view
        this.recyclerView.adapter = this.myAdapter


    }

    override fun onStart() {
        super.onStart()
        loadDataFromSharedPreferences()
    }

//    override fun onResume() {
//        super.onResume()
//        // Load and display data when the activity becomes visible
//        loadDataFromSharedPreferences()
//    }

    private fun loadDataFromSharedPreferences() {
        val sp : SharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val intLayout = sp.getInt("KEY_LAYOUT", LayoutType.LINEAR_VIEW_TYPE.ordinal);
        val boolHideLikes = sp.getBoolean("KEY_LIKE", hideLikeButtons)

        this.recyclerView.layoutManager = getLayoutManager(intLayout)

        this.myAdapter.setViewType(intLayout)
        this.myAdapter.setHideLikeBtn(boolHideLikes)

        this.recyclerView.adapter = this.myAdapter
    }

    /*
     * Just a method to return a specific LayoutManager based on the ViewType provided.
     * */
    private fun getLayoutManager(value: Int): RecyclerView.LayoutManager {
        if (value == LayoutType.LINEAR_VIEW_TYPE.ordinal)
            return LinearLayoutManager(this)
        else
            return GridLayoutManager(this, 2)
    }

    /*
    * Responsible for inflating the options menu on the upper right corner of the screen.
    * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /*
     * A little overkill tbh, but this method is responsible for handling the selection of items
     * in the options menu. There's only one item anyway -- Settings, which leads the user to the
     * Settings activity.
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val i = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}