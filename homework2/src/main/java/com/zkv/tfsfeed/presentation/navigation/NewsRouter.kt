package com.zkv.tfsfeed.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment

class NewsRouter(
    private val supportFragmentManager: FragmentManager,
    @IdRes private val containerId: Int = android.R.id.content
) {

    fun navigateToDetail(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_in_right,
                R.anim.slide_in_left,
                R.anim.slide_in_right
            )
            .add(containerId, DetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }
}
