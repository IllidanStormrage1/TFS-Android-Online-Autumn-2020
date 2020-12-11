package com.zkv.tfsfeed.presentation.ui

import android.content.Intent
import android.content.Intent.*
import android.net.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.zkv.tfsfeed.BuildConfig
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.adapter.MainViewPagerAdapter
import com.zkv.tfsfeed.presentation.ui.creator.CreatorPostFragment
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment
import com.zkv.tfsfeed.presentation.ui.favorites.FavoritesFragment
import com.zkv.tfsfeed.presentation.ui.news.NewsFragment
import com.zkv.tfsfeed.presentation.ui.profile.ProfileFragment
import com.zkv.tfsfeed.presentation.utils.extensions.loadImage
import com.zkv.tfsfeed.presentation.utils.extensions.registerNetworkCallback
import com.zkv.tfsfeed.presentation.utils.extensions.unregisterNetworkCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.merge_item_post.*
import kotlinx.android.synthetic.main.partial_label_connection_error.*

class MainActivity : AppCompatActivity(R.layout.activity_main), MainActivityCallback {

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        initViewState()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkCallback?.let(::unregisterNetworkCallback)
        networkCallback = null
    }

    override fun navigateToDetail(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_in_right,
                R.anim.slide_in_left,
                R.anim.slide_in_right
            )
            .add(android.R.id.content, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToCreatorPost() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_in_bottom,
                R.anim.slide_in_top,
                R.anim.slide_in_bottom
            )
            .add(android.R.id.content, CreatorPostFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun shareNewsItem(item: NewsItem) {
        if (item.contentUrl != null)
            loadImage(item.contentUrl) {
                showIntentChooser(
                    createShareIntent(
                        item.text,
                        FileProvider.getUriForFile(
                            this,
                            AUTHORITY_PROVIDER,
                            it
                        )
                    )
                )
            }
        else
            showIntentChooser(createShareIntent(item.text))
    }

    private fun initViewState() {
        val fragments = listOf(
            NewsFragment.newInstance(),
            FavoritesFragment.newInstance(),
            ProfileFragment.newInstance()
        )
        initViewPager(fragments)
        initBottomNavigation()
        networkCallback = registerNetworkCallback(
            onAvailable = { runOnUiThread { label_connection_error.isVisible = false } },
            onLost = { runOnUiThread { label_connection_error.isVisible = true } }
        )
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
        startActivity(createChooser(shareIntent, resources.getString(R.string.text_share_intent)))
    }

    private fun createShareIntent(content: String, uri: Uri? = null): Intent =
        Intent(ACTION_SEND).apply {
            type = TEXT_INTENT_TYPE
            addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(EXTRA_TEXT, content)
            uri?.let {
                type = "$IMAGE_INTENT_TYPE,$TEXT_INTENT_TYPE"
                putExtra(EXTRA_STREAM, uri)
            }
        }

    companion object {
        private const val TEXT_INTENT_TYPE = "text/plain"
        private const val IMAGE_INTENT_TYPE = "image/jpeg"
        private const val AUTHORITY_PROVIDER = "${BuildConfig.APPLICATION_ID}.fileprovider"
    }
}
