package com.zkv.tfsfeed.presentation.base

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface BaseMvpView<S, E> : MvpView {

    @AddToEndSingle
    fun render(state: S)

    @OneExecution
    fun renderEvent(event: E)
}
