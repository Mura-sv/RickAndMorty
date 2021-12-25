package com.example.rickandmorty.data

import com.example.rickandmorty.data.model.characters.CharacterDto
import com.example.rickandmorty.data.model.episode.EpisodeDto
import com.example.rickandmorty.data.model.location.LocationDto
import com.example.rickandmorty.domain.model.*

// Character mappers

fun CharacterDto.toCharacters(): Characters {
    return Characters(
        id = id,
        name = name,
        image = image,
        status = status,
        species = species,
        origin = origin.name,
    )
}

fun CharacterDto.toSearchModel(): Search {
    return Search(
        id = id,
        name = name,
        image = image,
        created = created,
        dimension = null,
        episode = null,
        type = null,
        status = status,
        species = species,
        gender = gender,
        air_date = null
    )
}

fun CharacterDto.toCharacterDetail(): CharacterDetail {
    return CharacterDetail(
        id = id,
        name = name,
        image = image,
        species = species,
        status = status,
        origin = origin.name,
        gender = gender,
        created = created,
    )
}

// Episode mappers

fun EpisodeDto.toEpisodes(): Episodes {
    return Episodes(
        id = id,
        name = name,
        episode = episode
    )
}

fun EpisodeDto.toSearchModel(): Search {
    return Search(
        id = id,
        name = name,
        created = created,
        image = null,
        dimension = null,
        type = null,
        status = null,
        species = null,
        gender = null,
        episode = episode,
        air_date = air_date,
    )
}

fun EpisodeDto.toEpisodeDetail(): EpisodeDetail {
    return EpisodeDetail(
        id = id,
        name = name,
        episode = episode,
        air_date = air_date,
        created = created
    )
}


// Location mappers

fun LocationDto.toLocations(): Locations {
    return Locations(
        name = name,
        locationType = type,
        id = id
    )
}

fun LocationDto.toSearchModel(): Search {
    return Search(
        id = id,
        name = name,
        created = created,
        image = null,
        status = null,
        species = null,
        gender = null,
        dimension = dimension,
        type = type,
        air_date = null,
        episode = null,
    )
}

fun LocationDto.toLocationDetail(): LocationDetail {
    return LocationDetail(
        id = id,
        name = name,
        dimension = dimension,
        type = type,
        created = created
    )
}
