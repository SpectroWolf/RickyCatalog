package com.spectro.tech.rickycatalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.databinding.FragmentCharacterDetailBinding
import com.spectro.tech.rickycatalog.epoxy.EpisodeListController
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var epoxyController: EpisodeListController
    private lateinit var character: CharacterDTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        character = viewModel.character.value!!
        setupToolbar()

        initEpoxyRecyclerView()
        bindCharacterDetails()
        getEpisodeList()

    }

    private fun getEpisodeList() {
        val episodesId = character.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        viewModel.getEpisodeList(episodesId)

        viewModel.episodeList.observe(viewLifecycleOwner) {
            epoxyController.episodeList = it.data!!
        }

    }

    private fun initEpoxyRecyclerView() {
        epoxyController = EpisodeListController()
        binding.epoxyCharacterEpisodes.setControllerAndBuildModels(epoxyController)
    }

    private fun bindCharacterDetails() {
        binding.tvCharacterDetailsName.text = character.name
        binding.tvCharacterDetailsStatusAndSpecies.text =
            "${character.status.replaceFirstChar { it.titlecase(Locale.ROOT) }} - ${character.species}"
        binding.tvCharacterDetailsOrigin.text = character.origin.name
        binding.tvCharacterDetailsLocation.text = character.location.name

        setCharacterImage()
        setStatusImage()
        setGenderImage()
    }

    private fun setStatusImage() {

        val imageId = when (character.status) {
            "Alive" -> R.drawable.ic_alive_icon
            "Dead" -> R.drawable.ic_dead_icon
            else -> R.drawable.ic_unkwon_status_icon
        }

        Glide.with(binding.ivCharacterDetailsStatusIcon)
            .load(imageId)
            .into(binding.ivCharacterDetailsStatusIcon)

    }

    private fun setGenderImage() {

        val imageId = if (character.gender.contentEquals("Male")) {
            R.drawable.baseline_male_24
        } else {
            R.drawable.baseline_female_24
        }

        Glide.with(binding.ivGenderIcon)
            .load(imageId)
            .into(binding.ivGenderIcon)
    }

    private fun setCharacterImage() {
        Glide.with(binding.ivCharacterDetailsImage)
            .load(character.image)
            .error(R.drawable.ic_generic_avatar)
            .into(binding.ivCharacterDetailsImage)
    }

    private fun setupToolbar() {
        viewModel.setToolbarTitleText(character.name)
        viewModel.setToolbarVisibility(true)
        viewModel.setToolbarBackButtonVisibility(true)
    }
}

