package com.spectro.tech.rickycatalog.model.domain

data class CharacterDTO(
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: OriginDTO,
    val location: LocationDTO,
    val image: String = "",
    val episode: List<String> = listOf()
)

