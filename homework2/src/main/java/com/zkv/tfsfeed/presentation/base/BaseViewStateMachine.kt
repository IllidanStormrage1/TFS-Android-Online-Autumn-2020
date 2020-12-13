package com.zkv.tfsfeed.presentation.base

abstract class BaseViewStateMachine<S, A, E> {

    abstract var state: S
        protected set

    var eventHandler: ((E) -> Unit)? = null

    abstract fun handleUpdate(action: A): S
}
