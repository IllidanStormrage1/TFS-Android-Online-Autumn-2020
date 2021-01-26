package com.zkv.tfsfeed.presentation.navigation

class ProfileNavigator {

    private var profileRouter: ProfileRouter? = null

    fun setProfileRouter(profileRouter: ProfileRouter) {
        this.profileRouter = profileRouter
    }

    fun navigateToCreatorPost() {
        profileRouter?.navigateToCreatorPost()
    }

    fun removeRouter() {
        profileRouter = null
    }
}
