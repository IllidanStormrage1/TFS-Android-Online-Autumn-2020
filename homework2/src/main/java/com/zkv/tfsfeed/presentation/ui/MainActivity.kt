package com.zkv.tfsfeed.presentation.ui

import android.content.Intent
import android.content.Intent.*
import android.net.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.zkv.tfsfeed.BuildConfig
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.data.api.AccessTokenHelper
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.App
import com.zkv.tfsfeed.presentation.adapter.MainViewPagerAdapter
import com.zkv.tfsfeed.presentation.extensions.loadImage
import com.zkv.tfsfeed.presentation.extensions.registerNetworkCallback
import com.zkv.tfsfeed.presentation.extensions.unregisterNetworkCallback
import com.zkv.tfsfeed.presentation.ui.creator.CreatorPostFragment
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import com.zkv.tfsfeed.presentation.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.partial_label_connection_error.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityCallback {

    @Inject
    lateinit var accessTokenHelper: AccessTokenHelper
    private var _networkCallback: ConnectivityManager.NetworkCallback? = null
    private val networkCallback: ConnectivityManager.NetworkCallback get() = _networkCallback!!

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!VK.isLoggedIn() || accessTokenHelper.isTokenExpired())
            VK.login(this, listOf(VKScope.WALL, VKScope.FRIENDS, VKScope.OFFLINE))
        else
            initViewState()
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                accessTokenHelper.saveToken(token.accessToken)
                initViewState()
            }

            override fun onLoginFailed(errorCode: Int) {
                finish()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
            super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkCallback(networkCallback)
        _networkCallback = null
    }

    override fun navigateToDetail(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_in_left,
                R.anim.slide_in_right,
                R.anim.slide_in_left)
            .add(android.R.id.content, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToCreatorPost() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_top,
                R.anim.slide_in_bottom,
                R.anim.slide_in_top,
                R.anim.slide_in_bottom)
            .add(android.R.id.content, CreatorPostFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun shareNewsItem(item: NewsItem) {
        if (item.photoUrl != null)
            loadImage(item.photoUrl) {
                showIntentChooser(createShareIntent(item.text, FileProvider.getUriForFile(this,
                    AUTHORITY_PROVIDER,
                    it)))
            }
        else
            showIntentChooser(createShareIntent(item.text))
    }

    private fun initViewState() {
        main_progress_bar.isVisible = false
        val fragments = listOf(NewsFragment.newInstance(),
            FavoritesFragment.newInstance(),
            ProfileFragment.newInstance())
        initViewPager(fragments)
        initBottomNavigation()
        _networkCallback = registerNetworkCallback(
            onAvailable = { runOnUiThread { label_connection_error.isVisible = false } },
            onLost = { runOnUiThread { label_connection_error.isVisible = true } })
    }

    private fun initViewPager(fragments: List<Fragment>) {
        val adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)
        main_view_pager.run {
            isUserInputEnabled = false
            this.adapter = adapter
            adapter.submitFragments(fragments)
        }
    }

    private fun initBottomNavigation() {
        main_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_news -> main_view_pager.setCurrentItem(0, false)
                R.id.menu_favorites -> main_view_pager.setCurrentItem(1, false)
                R.id.menu_profile -> main_view_pager.setCurrentItem(2, false)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun showIntentChooser(shareIntent: Intent) {
        startActivity(createChooser(shareIntent,
            resources.getString(R.string.text_share_intent)))
    }

    private fun createShareIntent(content: String, uri: Uri? = null): Intent =
        Intent(ACTION_SEND).apply {
            type = "text/plain"
            addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(EXTRA_TEXT, content)
            uri?.let {
                type = "image/jpeg,text/plain"
                putExtra(EXTRA_STREAM, uri)
            }
        }

    companion object {
        private const val AUTHORITY_PROVIDER = "${BuildConfig.APPLICATION_ID}.fileprovider"
    }
}
