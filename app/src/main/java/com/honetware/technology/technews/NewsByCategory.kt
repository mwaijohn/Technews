package com.honetware.technology.technews

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import utils.Constants
import utils.Utils

class NewsByCategory : AppCompatActivity() {

    var context: Context? = null
    var recyclerView: RecyclerView? = null
    var empty_view: TextView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val category = intent.getStringExtra(Constants.category)
        title = category;

        context = applicationContext
        recyclerView = findViewById(R.id.recycler_view)
        empty_view = findViewById(R.id.empty_view)
        swipeRefreshLayout = findViewById(R.id.swipe_container)

        Utils.generalCategories(context, recyclerView, empty_view,
                category,100, swipeRefreshLayout)

    }
}
