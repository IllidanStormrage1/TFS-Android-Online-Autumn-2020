package com.zkv.tfsfeed.presentation.base

abstract class BaseViewStateMachine<S, A> {
    abstract var state: S
        protected set

    abstract fun handleUpdate(action: A): S
}
