package com.spectro.tech.rickycatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.color.MaterialColors
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.ActivityMainBinding
import com.spectro.tech.rickycatalog.util.gone
import com.spectro.tech.rickycatalog.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initNavigation()
        initObservables()
        setTheme()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    private fun changeStatusBarColorForSplashScreen() {
        window.statusBarColor = getColor(R.color.soft_black)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())

            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun changeStatusBarColorToDefault() {

        window.statusBarColor = MaterialColors.getColor(
            binding.root,
            androidx.appcompat.R.attr.colorPrimary
        )

        WindowCompat.setDecorFitsSystemWindows(window, true)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            show(WindowInsetsCompat.Type.systemBars())

            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        }
    }

    private fun setToolbarVisibility(toolbarVisibility: Boolean) {
        if (toolbarVisibility) {
            binding.materialToolbar.visible()
        } else {
            binding.materialToolbar.gone()
        }
    }

    private fun setToolbarBackButtonVisibility(toolbarBackButtonVisibility: Boolean) {

        if (toolbarBackButtonVisibility) {
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        } else {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun initObservables() {

        viewModel.toolbarIsvisible.observe(this) {
            setToolbarVisibility(it)
        }

        viewModel.toolbarTitleText.observe(this) {
            supportActionBar?.title = it

        }

        viewModel.toolbarBackButtonIsVisible.observe(this) {
            setToolbarBackButtonVisibility(it)
        }

        viewModel.statusBarIsVisible.observe(this) {
            if (it) {
                changeStatusBarColorToDefault()
            } else {
                changeStatusBarColorForSplashScreen()
            }
        }
    }

    private fun setTheme() {

        viewModel.showDarkMode()

        viewModel.darkMode.observe(this) {
            if (it == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.themeSwitch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.themeSwitch.isChecked = false
            }
        }

        binding.themeSwitch.setOnClickListener {
            if (binding.themeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.setDarkMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.setDarkMode(false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}