package com.zkv.tfsfeed.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialContainerTransform
import com.zkv.tfsfeed.domain.model.NewsItem
import com.zkv.tfsfeed.presentation.ui.detail.DetailFragment

class NewsRouter(
    private val supportFragmentManager: FragmentManager,
    @IdRes private val containerId: Int = android.R.id.content
) {

    fun navigateToDetail(item: NewsItem) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(containerId, DetailFragment.newInstance(item).apply { sharedElementEnterTransition = MaterialContainerTransform() })
            .addToBackStack(null)
            .commit()
    }
}
