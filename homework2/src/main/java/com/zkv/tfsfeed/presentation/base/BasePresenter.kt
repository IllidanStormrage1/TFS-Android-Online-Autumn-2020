package com.zkv.tfsfeed.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    operator fun Disposable.plusAssign(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}