package com.example.homework2.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homework2.domain.utils.clearAndAddAll

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = mutableListOf<Fragment>()

    fun submitFragments(newFragments: List<Fragment>) {
        fragments.clearAndAddAll(newFragments)
        notifyDataSetChanged()
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}