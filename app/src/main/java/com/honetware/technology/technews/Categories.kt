package com.honetware.technology.technews

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.honetware.technology.technews.databinding.ActivityCategoriesBinding
import utils.Constants

class Categories : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val binding = DataBindingUtil.setContentView<ActivityCategoriesBinding>(this,R.layout.activity_categories);

        val handler = CardClickHandlers(this)
        binding.handler = handler
        binding.constants = Constants();
    }

    class CardClickHandlers {
        var context:Context? = null

        constructor(context: Context){
            this.context = context
        }

        fun entertainment(view: View,category:String){
            val intent = Intent(context, NewsByCategory::class.java)
            intent.putExtra(Constants.category, category)
            context?.startActivity(intent)

        }

    }
}
