package com.spectro.tech.rickycatalog.epoxy

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.util.withModelsFrom

class EpisodeListController() : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var episodeList = emptyList<EpisodeDTO>()
        set(value) {
            field = value
            if (field.isNotEmpty()) {
                isLoading = false
                requestModelBuild()
            }
        }


    override fun buildModels() {

        if (isLoading) {
            addLoadingState()
            return
        }

        if (episodeList.isEmpty()) {
            //todo error state
            return
        }

        val list = episodeList

        carousel {

            id("carousel")
            numViewsToShowOnScreen(1.25f)
            hasFixedSize(false)

            withModelsFrom(list) {
                EpisodeEpoxyModel_()
                    .id(it.id)
                    .episode(it)
            }
        }
    }

    private fun addLoadingState() {
        EpoxyLoadingState_()
            .id("Loading")
            .spanSizeOverride { _, _, _ -> 1 }
            .addTo(this)
    }
}