package com.example.travelfriend.database.model

data class Image(val num: String, val path: String) {
    constructor() : this("", "")
}