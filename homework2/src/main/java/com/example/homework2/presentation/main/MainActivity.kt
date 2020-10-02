package com.example.homework2.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.homework2.R
import com.example.homework2.presentation.list.utils.DividerIItemDecoration
import com.example.homework2.presentation.list.utils.MainItemTouchHelper
import com.example.homework2.presentation.list.PostsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PostsAdapter()
        main_posts_rv.adapter = adapter
        main_posts_rv.addItemDecoration(
            DividerIItemDecoration(
                resources.getDimensionPixelSize(R.dimen.default_margin),
                resources.getDimension(R.dimen.header_text_size),
                ContextCompat.getColor(this, R.color.colorPrimary),
                adapter
            )
        )
        val ith = ItemTouchHelper(MainItemTouchHelper(adapter))
        ith.attachToRecyclerView(main_posts_rv)

        main_posts_srl.setOnRefreshListener { mainViewModel.onRefresh() }

        mainViewModel.postsLiveData.observe(this) {
            when (it) {
                is UIState.Loading -> main_posts_srl.isRefreshing = it.isLoad
                is UIState.Success -> adapter.submitList(it.payload)
            }
        }
    }
}
