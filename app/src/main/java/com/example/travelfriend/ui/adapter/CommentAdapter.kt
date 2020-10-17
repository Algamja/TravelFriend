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
import com.example.travelfriend.database.model.uidcomment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.net.URL


class CommentAdapter(val context: Context) : RecyclerView.Adapter<Holder1>() {
    var listData = mutableListOf<uidcomment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return Holder1(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder1, position: Int) {
        holder.commenttext.text=listData[position].comment
        val imageurl = listData[position].image1
        Glide.with(context).load(imageurl).into(holder.imageget)
    }

}

class Holder1(itemView: View):RecyclerView.ViewHolder(itemView){
    val commenttext = itemView.c_text
    val imageget= itemView.c_image
}