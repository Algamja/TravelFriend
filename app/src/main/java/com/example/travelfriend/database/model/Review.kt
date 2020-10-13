package com.example.travelfriend.database.model

data class Review(
    var writer: String,
    var comment: Map<String,String>,
    var like: Map<String,Boolean>,
    var image: Map<String,String>,
    var number:String
) {
    constructor() : this(
        "",
        hashMapOf<String, String>(),
        hashMapOf<String,Boolean>(),
        hashMapOf<String, String>(),
        ""
    )
}