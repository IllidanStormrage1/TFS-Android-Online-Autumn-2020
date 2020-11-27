package com.zkv.tfsfeed.presentation.utils.extensions

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.showIfNotVisible(manager: FragmentManager, tag: String?) {
    manager.findFragmentByTag(tag) ?: show(manager, tag)
}

val isQHigher: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

inline fun <T : Fragment> T.withArgs(
    argsBuilder: Bundle.() -> Unit,
): T = apply { arguments = Bundle().apply(argsBuilder) }