package com.example.homework2.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homework2.R
import com.example.homework2.domain.model.Post
import com.example.homework2.presentation.detail.DetailFragment
import com.example.homework2.presentation.favorites.FavoritesFragment
import com.example.homework2.presentation.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentNavigationCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragments = listOf(NewsFragment.newInstance(), FavoritesFragment.newInstance())
        initViewPager(fragments)
        initBottomNavigation()
    }

    private fun initViewPager(fragments: List<Fragment>) {
        val adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.submitFragments(fragments)
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

    override fun navigateToDetail(item: Post) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit)
            .add(android.R.id.content, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }
}
