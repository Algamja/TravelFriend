package com.example.travelfriend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.ReviewAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = ReviewAdapter({}, {}, this@HomeActivity)

        val reviews :MutableList<Review> = mutableListOf()

        FirebaseDatabase.getInstance().reference.child("Review")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for(i in dataSnapshot.children){
                        FirebaseDatabase.getInstance().reference.child("Review").child(i.key?:"").addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onCancelled(error: DatabaseError) {

                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                val review = Review()
                                for(j in snapshot.children){
                                    if(j.key == "comment"){
                                        review.comment =j.value as HashMap<String, String>
                                    }else if(j.key == "image"){
                                        review.image = j.value as HashMap<String, String>
                                    } else if (j.key == "like"){
                                        review.like = j.value as HashMap<String, Boolean>
                                    } else{
                                        review.writer = j.value as String
                                    }
                                }
                                reviews.add(review)
                                adapter.setReviews(reviews)
                                val layoutManager = LinearLayoutManager(this@HomeActivity)
                                home_recycler_view.adapter = adapter
                                home_recycler_view.layoutManager = layoutManager
                                home_recycler_view.setHasFixedSize(true)
                            }

                        })

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
    }
}
