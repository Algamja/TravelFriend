package com.example.travelfriend.database.model

data class Comment(val writer: String, val comment: String) {
    constructor() : this("", "")
}