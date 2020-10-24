package com.example.travelfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.CommentAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val getReview = intent.getSerializableExtra("reviewData")
        val review = getReview as Review
        val adapter = CommentAdapter(review.comment.values, this)
        comment_recyclerView.adapter = adapter
        comment_recyclerView.layoutManager = LinearLayoutManager(this)

        comment_submit_button.setOnClickListener {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    val comment = comment_edit_text.text.toString()
                    comment_edit_text.text = null
                    review.comment[""] =
                        mapOf(FirebaseAuth.getInstance().currentUser!!.uid to comment)
                    adapter.notifyDataSetChanged()

                    FirebaseDatabase.getInstance().reference.child("Review")
                        .child(review.number).child("comment").push()
                        .setValue(mapOf(FirebaseAuth.getInstance().currentUser!!.uid to comment))

                    FirebaseDatabase.getInstance().reference.child("Review")
                        .child(review.number).child("comment")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {}
                            override fun onDataChange(snapshot: DataSnapshot) {
                                review.comment = snapshot.value as MutableMap<String, Map<String, String>>
                            }
                        })
                }
            }
        }
    }
}