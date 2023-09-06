package com.spectro.tech.rickycatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.adapter.TabLayoutAdapter
import com.spectro.tech.rickycatalog.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showStatusBar(true)
        setupToolbar()
        configTablayout()
    }

    private fun configTablayout() {
        val adapter = TabLayoutAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            }
        )
        adapter.addFragment(
            CharacterListFragment(),
            resources.getString(R.string.character_list_fragment_title)
        )
        adapter.addFragment(
            FavoriteFragment(),
            resources.getString(R.string.favorites_fragment_title)
        )
        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = adapter.getTitle(
                position
            )
        }.attach()
    }

    private fun showStatusBar(show: Boolean) {
        viewModel.showStatusBar(show)
    }

    private fun setupToolbar() {
        viewModel.setToolbarTitleText("Characters")
        viewModel.setToolbarVisibility(true)
        viewModel.setToolbarBackButtonVisibility(false)
    }
}