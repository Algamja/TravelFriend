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

        val getReview = intent.getSerializableExtra("reviewData")   //reviews를 가져옴

        var adapter = CommentAdapter(this@CommentActivity)
        var data2 = getReview as Review //data2변수에 review를 넣어줌
        adapter.Data = data2.comment //adapter에 review에 comment 부분만 넘겨주었음
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@CommentActivity)

        val edit1 = edit1.text.toString()
        data2.comment.put(FirebaseAuth.getInstance().currentUser!!.uid, edit1)
        //입력해준 값을 data2의 comment에 put하여줌
    }
}


