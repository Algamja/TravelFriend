package com.example.travelfriend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.ReviewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.*


class HomeActivity : AppCompatActivity() {

    private lateinit var adapter: ReviewAdapter
    val reviews: MutableList<Review> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseDatabase.getInstance().reference.child("Review")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    reviews.clear()
                    for (i in dataSnapshot.children) {
                        FirebaseDatabase
                            .getInstance()
                            .reference
                            .child("Review")
                            .child(i.key ?: "")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {}

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val review = Review()
                                    for (j in snapshot.children) {
                                        when (j.key) {
                                            "comment" -> {
                                                review.comment = j.value as HashMap<String, String>
                                            }
                                            "image" -> {
                                                review.image = j.value as HashMap<String, String>
                                            }
                                            "like" -> {
                                                review.like = j.value as HashMap<String, Boolean>
                                            }
                                            "writer" -> {
                                                review.writer = j.value as String
                                            }
                                            else -> {
                                                review.number = j.value as String
                                            }
                                        }
                                    }
                                    reviews.add(review)
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

        adapter = ReviewAdapter(reviews, ::onClickLike, {
            startActivity(Intent(this, CommentActivity::class.java))
        }, this)
    }

    private fun setLikeToReviews(review: Review) {
        for (i in 0 until reviews.size) {
            if (reviews[i].number == review.number) {
                reviews[i].like =
                    mapOf(
                        FirebaseAuth.getInstance().currentUser!!.uid to !(reviews[i].like[FirebaseAuth.getInstance().currentUser!!.uid]
                            ?: false)
                    )
            }
        }
    }

    private fun onClickLike(review: Review) {
        setLikeToReviews(review)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                for (r in reviews) {
                    if (r.number == review.number) {
                        FirebaseDatabase.getInstance().reference.child("Review")
                            .child(review.number).child("like")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(r.like[FirebaseAuth.getInstance().currentUser!!.uid])
                    }
                }
            }
        }
    }
}
