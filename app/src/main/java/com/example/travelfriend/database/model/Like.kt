package com.example.travelfriend.database.model

data class Like(val writer: String, val like: Boolean) {
    constructor() : this("", false)
}