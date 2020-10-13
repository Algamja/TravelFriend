package com.example.travelfriend.util

import com.example.travelfriend.database.model.Review
import com.google.firebase.auth.FirebaseAuth

class LikeCompare {
    fun likeCompare(review: Review):Boolean{
        for(like in review.like){
            return FirebaseAuth.getInstance().currentUser!!.uid == like.key && like.value
        }
        return false
    }
}