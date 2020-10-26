package com.zkv.tfsfeed.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.data.AccessTokenHelper
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.adapter.MainViewPagerAdapter
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment
import com.zkv.tfsfeed.presentation.ui.dialog.ErrorDialogFragment
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityCallback {

    @Inject
    lateinit var accessTokenHelper: AccessTokenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!VK.isLoggedIn())
            VK.login(this, listOf(VKScope.WALL, VKScope.FRIENDS))
        else
            initUi()
    }

    private fun initUi() {
        main_progress_bar.isVisible = false
        val fragments = listOf(NewsFragment.newInstance(), FavoritesFragment.newInstance())
        initViewPager(fragments)
        initBottomNavigation()
    }

    private fun initViewPager(fragments: List<Fragment>) {
        val adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)
        main_view_pager.adapter = adapter
        main_view_pager.isUserInputEnabled = false
        adapter.submitFragments(fragments)
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

    override fun navigateToDetail(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter, R.anim.exit)
            .add(android.R.id.content, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

    override fun showErrorDialog(throwable: Throwable) {
        ErrorDialogFragment.newInstance(throwable.message).show(supportFragmentManager, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                accessTokenHelper.saveToken(token.accessToken)
                initUi()
            }

            override fun onLoginFailed(errorCode: Int) {}
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
            super.onActivityResult(requestCode, resultCode, data)
    }
}
