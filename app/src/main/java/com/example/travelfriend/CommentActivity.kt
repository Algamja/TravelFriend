package com.example.travelfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.CommentAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val getReview = intent.getSerializableExtra("reviewData")

        var adapter = CommentAdapter(this@CommentActivity)
        var data2 =getReview as Review
        adapter.Data = data2.comment
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CommentActivity)

        val edit1 = edit1.text.toString()
        data2.comment.put(FirebaseAuth.getInstance().currentUser!!.uid, edit1)

    }
}


