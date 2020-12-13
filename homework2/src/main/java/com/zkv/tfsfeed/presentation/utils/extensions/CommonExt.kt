package com.zkv.tfsfeed.presentation.utils.extensions

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment

val isQHigher: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

inline fun <T : Fragment> T.withArgs(
    argsBuilder: Bundle.() -> Unit,
): T = apply { arguments = Bundle().apply(argsBuilder) }
