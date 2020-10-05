package com.example.homework2.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.homework2.R
import com.example.homework2.presentation.list.PostsAdapter
import com.example.homework2.presentation.list.utils.DividerItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PostsAdapter { mainViewModel.like(it) }
        initRecyclerView(adapter)
        observeViewModel(adapter)
    }

    private fun observeViewModel(adapter: PostsAdapter) {
        mainViewModel.postsLiveData.observe(this) {
            when (it) {
                is UIState.Loading -> main_posts_srl.isRefreshing = it.isLoad
                is UIState.Success -> adapter.submitList(it.payload)
            }
        }
    }

    private fun initRecyclerView(adapter: PostsAdapter) {
        main_posts_rv.adapter = adapter
        main_posts_rv.addItemDecoration(
            DividerItemDecoration(
                verticalSpace = resources.getDimensionPixelSize(R.dimen.default_margin),
                headerTextSize = resources.getDimension(R.dimen.header_text_size),
                textColor = ContextCompat.getColor(this, R.color.colorLightBlue),
                adapter
            )
        )
        ItemTouchHelper(MainItemTouchHelper(adapter)).also { it.attachToRecyclerView(main_posts_rv) }
        main_posts_srl.setOnRefreshListener { mainViewModel.onRefresh() }
    }
}
