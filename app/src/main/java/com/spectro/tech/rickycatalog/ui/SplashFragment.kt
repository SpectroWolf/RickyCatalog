package com.spectro.tech.rickycatalog.ui

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        showStatusBar(false)
        endAnimationListener()
    }

    private fun showStatusBar(show: Boolean) {
        viewModel.showStatusBar(show)
    }

    private fun setupToolbar() {
        viewModel.setToolbarTitleText("")
        viewModel.setToolbarVisibility(false)
        viewModel.setToolbarBackButtonVisibility(false)
    }

    private fun goToCharacterList() {
        findNavController().navigate(R.id.action_splashFragment_to_characterListFragment)
    }

    private fun endAnimationListener() {
        binding.lottieSplashScreen.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                goToCharacterList()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}