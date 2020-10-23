package com.example.travelfriend.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelfriend.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.net.URL


class CommentAdapter(val context: Context) : RecyclerView.Adapter<Holder1>() {
    var Data = mapOf<String, String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder1 {    //어떤 data와 xml을 bind 시켜주는 역할(어떤 item을 쓰는지 정해줄 수 있음)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return Holder1(view)
    }

    override fun getItemCount(): Int {
        return Data.size
    }

    override fun onBindViewHolder(holder: Holder1, position: Int) {
        val commentList = Data.values.toTypedArray()
        val uid = Data.keys.toTypedArray()[position]
        FirebaseDatabase.getInstance().reference.child("User").child(uid).child("image").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                holder.commenttext.text=commentList[position]
                val imageUrl = snapshot.value as String
                Glide.with(context).load(imageUrl).into(holder.imageget)
            }

        })
    }

}

class Holder1(itemView: View):RecyclerView.ViewHolder(itemView){
    val commenttext = itemView.c_text
    val imageget= itemView.c_image
}