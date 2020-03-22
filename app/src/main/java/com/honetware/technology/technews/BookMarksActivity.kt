package com.honetware.technology.technews

import adapter.BookMarksAdapter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honetware.technology.technews.persistence.BookMark
import com.honetware.technology.technews.persistence.DataBaseCallback
import com.honetware.technology.technews.persistence.DataStore

class BookMarksActivity : AppCompatActivity(),DataBaseCallback {

    private lateinit var text_view:TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_marks)
        text_view = findViewById<TextView>(R.id.empty_view)

        text_view.visibility = View.VISIBLE

        val dataStore = DataStore.getInstance(this)
        dataStore.getBookmarks(this)

    }

    override fun onBooksmarksLoaded(bookMarkList: MutableList<BookMark>?) {

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val bookMarksAdapter = BookMarksAdapter(bookMarkList,this)
        val mLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = bookMarksAdapter

        if((bookMarkList?.size) == 0){
            text_view.visibility = View.VISIBLE
        }else{
            text_view.visibility = View.GONE
        }
    }
}
