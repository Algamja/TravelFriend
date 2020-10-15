package com.example.travelfriend.util

import com.example.travelfriend.database.model.Review
import com.google.firebase.auth.FirebaseAuth

class LikeCompare {
    fun likeCompare(review: Review):Boolean{
        for(i in review.like){
            if( FirebaseAuth.getInstance().currentUser!!.uid == i.key && i.value){
                return true
            }
        }
        return false
    }
}