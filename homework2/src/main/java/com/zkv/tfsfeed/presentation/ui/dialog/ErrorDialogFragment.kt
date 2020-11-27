package com.zkv.tfsfeed.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zkv.tfsfeed.R
import com.zkv.tfsfeed.presentation.utils.extensions.withArgs

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.text_error_dialog)
            .setMessage(requireArguments().getString(ERROR_MESSAGE_KEY)
                ?: getString(R.string.message_error_default))
            .setPositiveButton(R.string.text_positive_button_error_dialog) { _, _ -> dismiss() }
            .setCancelable(true)
            .create()

    companion object {
        const val ERROR_MESSAGE_KEY = "messageKey"

        fun newInstance(errorMessage: String?) = ErrorDialogFragment().withArgs {
            putString(ERROR_MESSAGE_KEY, errorMessage)
        }
    }
}