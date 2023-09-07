package com.spectro.tech.rickycatalog.epoxy

import com.airbnb.epoxy.EpoxyModel
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
}
