package com.example.rickandmorty.extensions

import java.text.SimpleDateFormat


fun dateConverter(date: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val formattedDate = formatter.format(parser.parse(date))

    return formattedDate

}

