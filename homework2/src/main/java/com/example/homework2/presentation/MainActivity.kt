package com.example.homework2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.R
import com.example.homework2.presentation.favorites.FavoritesFragment
import com.example.homework2.presentation.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()
        initAdapter()
    }

    private fun initAdapter() {
        val adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.submitFragments(listOf(NewsFragment.newInstance(), FavoritesFragment.newInstance()))
        main_view_pager.isUserInputEnabled = false
        main_view_pager.adapter = adapter
    }

    private fun initBottomNavigation() {
        main_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_news -> main_view_pager.setCurrentItem(0, true)
                R.id.menu_favorites -> main_view_pager.setCurrentItem(1, true)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
