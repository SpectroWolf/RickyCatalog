package com.spectro.tech.rickycatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.FragmentCharacterListBinding
import com.spectro.tech.rickycatalog.epoxy.CharacterListController
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var epoxyController: CharacterListController

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
        initEpoxyRecyclerView()

    }

    private fun initEpoxyRecyclerView() {
        epoxyController = CharacterListController(::getCharacterId)


        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<CharacterDTO> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyList.setController(epoxyController)

    }

    private fun getCharacterId(characterDTO: CharacterDTO) {
        viewModel.setCharacter(characterDTO)
        findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment)
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