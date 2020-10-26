package com.zkv.tfsfeed.presentation.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zkv.tfsfeed.presentation.App
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment(layoutRes) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.appComponent.inject(this)
    }
}