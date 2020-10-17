package com.example.travelfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.uidcomment
import com.example.travelfriend.ui.adapter.CommentAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {
    lateinit var post : uidcomment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

            button1.setOnClickListener(){
                val myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().currentUser!!.uid)
                val comment2 = edit1.text.toString()
                val input1 = uidcomment("https://firebasestorage.googleapis.com/v0/b/trip-friend-6e3e3.appspot.com/o/cat.jpg?alt=media&token=429dfb7d-5962-4209-ac5b-05e20c30f92c",comment2)
                myRef.setValue(input1)

//                myRef.addValueEventListener(object : ValueEventListener {
//                    override fun onCancelled(error: DatabaseError) {
//                        println("Failed to read value.")
//                    }
//
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다.
//                        val value = snapshot?.value
//                        text3.text = "$value"
//                    }
//                })
                val database1 = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().currentUser!!.uid)
                database1.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        post = dataSnapshot.getValue(uidcomment::class.java)?: uidcomment()
                            //데이터를 클래스 형태로 가져와줌
                        var data: MutableList<uidcomment> = setData()
                        var adapter = CommentAdapter(this@CommentActivity)
                        adapter.listData = data
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this@CommentActivity)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
            }
        }
        fun setData():MutableList<uidcomment>{
            var data : MutableList<uidcomment> = mutableListOf()
            var uiddata = post
            data.add(uiddata)
            return data
        }
    }


