package com.spectro.tech.rickycatalog.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.OnModelBuildFinishedListener
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO

class CharacterListController(
    val getCharacterID: (CharacterDTO) -> Unit
) : PagingDataEpoxyController<CharacterDTO>() {

    override fun buildItemModel(currentPosition: Int, item: CharacterDTO?): EpoxyModel<*> {
        return CharacterEpoxyModel_()
            .id(item?.id)
            .character(item)
            .buttonCallBack {
                getCharacterID(it)
            }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if(models.isEmpty()) {
            EpoxyLoadingState_()
                .id("Loading")
                .spanSizeOverride { _, _, _ -> 2 }
                .addTo(this)
            return
        }

            super.addModels(models)
        }

}
