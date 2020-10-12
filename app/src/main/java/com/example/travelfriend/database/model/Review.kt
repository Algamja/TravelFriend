package com.example.travelfriend.database.model

data class Review(
    val writer: String,
    val comment: MutableList<Comment>,
    val like: MutableList<Like>,
    val image: MutableList<Image>
) {
    constructor() : this(
        "",
        mutableListOf<Comment>(Comment()),
        mutableListOf<Like>(Like()),
        mutableListOf<Image>(Image())
    )
}