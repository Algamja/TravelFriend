package com.example.travelfriend.ui.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelfriend.R
import com.example.travelfriend.database.model.Comment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.net.URL


class CommentAdapter : RecyclerView.Adapter<CommentAdapter.Holder>() {
    var listData = mutableListOf<Comment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.c_text1.text = listData[position].comment

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("User").child("image")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다.
                val value = snapshot?.value
                val url = URL(value as String?)
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                holder.c_image1.setImageBitmap(bmp)
            }
        })

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var c_image1 = itemView.c_image
            var c_text1 = itemView.c_text
            var c_button1 = itemView.c_button

        }
    }

