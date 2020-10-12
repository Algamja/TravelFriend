package com.example.travelfriend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.ReviewAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter:ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("Review")

        val reviews:MutableList<Review> = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val review: Review = dataSnapshot.getValue<Review>(Review::class.java)?:Review()
                reviews.add(review)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        adapter = ReviewAdapter({}, {},this@HomeActivity)
        adapter.setReviews(reviews)

    }
}
