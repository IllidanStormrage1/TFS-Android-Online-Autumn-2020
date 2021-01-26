package com.zkv.tfsfeed.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.ui.creator.CreatorPostFragment

class ProfileRouter(
    private val supportFragmentManager: FragmentManager,
    @IdRes private val containerId: Int = android.R.id.content
) {

    fun navigateToCreatorPost() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_in_bottom,
                R.anim.slide_in_top,
                R.anim.slide_in_bottom
            )
            .add(containerId, CreatorPostFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}
