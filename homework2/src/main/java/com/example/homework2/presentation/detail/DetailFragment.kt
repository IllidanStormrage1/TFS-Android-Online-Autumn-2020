package com.example.homework2.presentation.detail

import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.homework2.R
import com.example.homework2.domain.model.Post
import com.example.homework2.presentation.view.loadFromUrl
import kotlinx.android.synthetic.main.merge_item_post.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Post>(KEY_ITEM)?.let(::initView)
    }

    private fun initView(item: Post) {
        item.run {
            content_tv.autoLinkMask = Linkify.ALL
            content_tv.setTextIsSelectable(true)
            content_tv.linksClickable = true
            group_name_tv.text = displayName
            post_creation_date_tv.text = date
            content_tv.text = text
            like_btn.text = likesCount.toString()
            comment_btn.text = commentsCount.toString()
            share_btn.text = repostsCount.toString()
            avatar_iv.loadFromUrl(avatarUrl, R.drawable.avatar_placeholder)
            if (item.photoUrl == null)
                content_iv.isVisible = false
            else content_iv.loadFromUrl(
                photoUrl,
                R.drawable.image_placeholder)
            if (isFavorite)
                like_btn.setIconResource(R.drawable.ic_heart_selected)
            else
                like_btn.setIconResource(R.drawable.ic_heart)
        }
    }

    companion object {

        const val KEY_ITEM = "item"

        fun newInstance(item: Post) = DetailFragment().apply {
            arguments = bundleOf(KEY_ITEM to item)
        }
    }
}