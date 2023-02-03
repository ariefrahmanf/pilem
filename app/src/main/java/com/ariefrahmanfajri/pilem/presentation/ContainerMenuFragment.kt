package com.ariefrahmanfajri.pilem.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentContainerMenuBinding

class ContainerMenuFragment : Fragment() {

    private lateinit var binding: FragmentContainerMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuNav =
            childFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        NavigationUI.setupWithNavController(binding.botNav, menuNav.navController)

        binding.botNav.setupWithNavController(menuNav.navController)

        menuNav.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment, R.id.tvShowFragment, R.id.settingFragment -> {
                    showNavBar()
                } else -> {
                    hideNavBar()
                }
            }
        }
    }

    private fun showNavBar() {
        binding.botNav.visibility = View.VISIBLE
    }

    private fun hideNavBar() {
        binding.botNav.visibility = View.GONE
    }
}