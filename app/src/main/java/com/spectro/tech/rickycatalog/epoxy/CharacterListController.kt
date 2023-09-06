package com.spectro.tech.rickycatalog.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO

class CharacterListController(
    val getCharacterID: (CharacterDTO) -> Unit
) : PagingDataEpoxyController<CharacterDTO>() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var characterList = emptyList<CharacterDTO>()
        set(value) {
            field = value
            if (field.isNotEmpty()) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: CharacterDTO?): EpoxyModel<*> {
        return CharacterEpoxyModel_()
            .id(item?.id)
            .character(item)
            .buttonCallBack {
                getCharacterID(it)
            }
    }

//    override fun buildModels() {
//
//        if (isLoading) {
//            addLoadingState()
//            return
//        }
//
//        if (characterList.isEmpty()) {
//            //todo error state
//            return
//        }
//
//        characterList.forEach {
//            addCharacter(it)
//        }
//    }
//
//    private fun addCharacter(character: CharacterDTO) {
//        CharacterEpoxyModel_()
//            .id(character.id)
//            .character(character)
//            .buttonCallBack {
//                getCharacterID(it)
//            }
//            .addTo(this)
//    }
//
//    private fun addLoadingState() {
//        EpoxyLoadingState_()
//            .id("Loading")
//            .spanSizeOverride { _, _, _ -> 2 }
//            .addTo(this)
//    }
}
