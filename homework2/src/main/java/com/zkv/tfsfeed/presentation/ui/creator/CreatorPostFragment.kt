package com.zkv.tfsfeed.presentation.ui.creator

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.utils.extensions.hideKeyboardFrom
import com.zkv.tfsfeed.presentation.utils.extensions.openKeyboardTo
import com.zkv.tfsfeed.presentation.utils.extensions.setOnDebounceClickListener
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatorPostFragment : Fragment(R.layout.fragment_create_post) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewState()
        initInsets()
    }

    private fun initInsets() {
        requireView().applySystemWindowInsetsToPadding(top = true)
    }

    private fun initViewState() {
        creator_send_iv.run {
            isEnabled = false
            setOnDebounceClickListener {
                setFragmentResult()
                close()
            }
        }
        creator_close_iv.setOnClickListener { close() }
        create_post_et.run {
            requireActivity().openKeyboardTo(this)
            addTextChangedListener(
                onTextChanged = { charSequence: CharSequence?, _: Int, _: Int, _: Int ->
                    creator_send_iv.isEnabled = !charSequence.isNullOrBlank()
                }
            )
        }
    }

    private fun setFragmentResult() {
        requireActivity().supportFragmentManager.setFragmentResult(
            RESULT_REQUEST_KEY,
            bundleOf(BUNDLE_KEY to create_post_et.text.toString())
        )
    }

    private fun close() {
        requireContext().hideKeyboardFrom(create_post_et)
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        const val RESULT_REQUEST_KEY = "resultKey"
        const val BUNDLE_KEY = "resultKey"

        fun newInstance() = CreatorPostFragment()
    }
}
