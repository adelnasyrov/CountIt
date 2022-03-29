package com.example.letscount.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.letscount.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var navController: NavController
    private var navigationView: NavigationView? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Plus"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_plus)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.nav_view)
        navigationView?.itemIconTintList = null
        bottomNavigationView = findViewById(R.id.bottom)
        navigationView?.setCheckedItem(R.id.plus)
        bottomNavigationView?.itemIconTintList = null
        navController = findNavController(R.id.nav_host_fragment_content_main)
        var currentItem = "plus"
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_plus)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_plus)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_plus)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_plus)
                    currentItem = "plus"
                    toolbar.title = "Plus"
                    true
                }
                R.id.item_2 -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_minus)
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_minus)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_minus)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_minus)
                    currentItem = "minus"
                    toolbar.title = "Minus"
                    true
                }
                R.id.item_3 -> {
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_multiplication)
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_multiplication)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_multiplication)
                    if (currentItem == "division")
                        navController.navigate(R.id.action_division_to_multiplication)
                    currentItem = "multiplication"
                    toolbar.title = "Multiplication"
                    true
                }
                R.id.item_4 -> {
                    if (currentItem == "plus")
                        navController.navigate(R.id.action_plus_to_division)
                    if (currentItem == "minus")
                        navController.navigate(R.id.action_minus_to_division)
                    if (currentItem == "multiplication")
                        navController.navigate(R.id.action_multiplication_to_division)
                    currentItem = "division"
                    toolbar.title = "Division"
                    true
                }
                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.plus -> showBottomNav()
                R.id.minus -> showBottomNav()
                R.id.multiplication -> showBottomNav()
                R.id.division -> showBottomNav()
                R.id.plus_game -> hideBottomNav()
                R.id.starter -> hideBottomNav()
                R.id.minus_game_1 -> hideBottomNav()
            }
        }

    }

    private fun showBottomNav() {
        bottomNavigationView?.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout?.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        currentObject = null
        super.onBackPressed()
    }

    override fun onDestroy() {
        currentObject = null
        super.onDestroy()
    }

    companion object {
        var currentObject: CountDownTimer? = null
    }

}