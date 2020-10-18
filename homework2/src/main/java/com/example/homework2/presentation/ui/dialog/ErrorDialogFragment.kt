package com.example.homework2.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.homework2.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_error_dialog)
            .setMessage(arguments?.getString(ERROR_MESSAGE_KEY)
                ?: getString(R.string.default_message_error_dialog))
            .setPositiveButton(R.string.text_positive_button_error_dialog) { _, _ -> dismiss() }
            .setCancelable(true)
            .create()

    companion object {

        const val ERROR_MESSAGE_KEY = "messageKey"

        fun newInstance(errorMessage: String?) = ErrorDialogFragment().apply {
            arguments = bundleOf(ERROR_MESSAGE_KEY to errorMessage)
        }
    }
}