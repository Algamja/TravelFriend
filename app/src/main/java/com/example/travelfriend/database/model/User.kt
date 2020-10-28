package com.example.travelfriend.database.model

import android.net.Uri


data class User(var email: String, var nickname: String, var name: String, var age: String, var gender: String, var phonenumber:String, var image: String) {

    constructor()
    :this("","","","","","","")

}

