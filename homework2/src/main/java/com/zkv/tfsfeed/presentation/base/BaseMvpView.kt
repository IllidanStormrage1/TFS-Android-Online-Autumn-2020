package com.zkv.tfsfeed.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface BaseMvpView<T> : MvpView {
    @AddToEndSingle
    fun render(state: T)
}