package com.example.rickandmorty

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.network_connection.NetworkStateReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private var networkStateReceiver: NetworkStateReceiver? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setNetworkStateReceiver()


    }


    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = binding.bottomNav
        bottomNav.setupWithNavController(navController)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)


//        val character = CharacterFragment().newInstance()
//        val location = LocationFragment().newInstance()
//        val episode = EpisodeFragment().newInstance()
//        val search = SearchFragment().newInstance()

        // bottom navigation

//        bottomNav.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.characterFragment -> currentFragment = character
//                R.id.locationFragment -> currentFragment = location
//                R.id.episodeFragment -> currentFragment = episode
//                R.id.searchFragment -> currentFragment = search
//            }
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment, currentFragment)
//                .commit()
//            true
//        }


        // removal of bottom nav on detailed fragment

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.isVisible =
                destination.id == R.id.characterFragment ||
                        destination.id == R.id.locationFragment ||
                        destination.id == R.id.episodeFragment ||
                        destination.id == R.id.searchFragment


            toolbar.isGone =
                destination.id == R.id.characterFragment ||
                        destination.id == R.id.locationFragment ||
                        destination.id == R.id.episodeFragment ||
                        destination.id == R.id.searchFragment
        }


        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }


    }


    private fun setNetworkStateReceiver() {
        networkStateReceiver = NetworkStateReceiver(this)
        networkStateReceiver!!.addListener(this)
        applicationContext.registerReceiver(networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }


    override fun onNetworkAvailable() {
        binding.navHostFragment.visibility = VISIBLE
        binding.noConnection.root.visibility = GONE
    }

    override fun onNetworkUnavailable() {
        binding.navHostFragment.visibility = GONE
        binding.noConnection.root.visibility = VISIBLE
    }
}
