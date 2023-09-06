package com.spectro.tech.rickycatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.spectro.tech.rickycatalog.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        showStatusBar(true)

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
