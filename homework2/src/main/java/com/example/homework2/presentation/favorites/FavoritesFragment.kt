package com.example.homework2.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.homework2.R

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        fun newInstance() = FavoritesFragment()
    }
}