package com.spectro.tech.rickycatalog.model.network

import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import com.spectro.tech.rickycatalog.model.domain.InfoDTO

data class CharacterListResponse (
    val info: InfoDTO,
    val results: List<CharacterDTO>
)