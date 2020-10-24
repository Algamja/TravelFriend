package com.example.travelfriend.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelfriend.R
import com.example.travelfriend.database.model.Plan
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.map_item.view.*

class PlanAdapter(val context: Context) : RecyclerView.Adapter<Holder2>() {
var listData = mutableListOf<Plan>()
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder2 {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.map_item,parent,false)
    return Holder2(view)
}

override fun getItemCount(): Int {
    return listData.size
}

override fun onBindViewHolder(holder: Holder2, position: Int) {
    holder.commenttext.text=listData[position].p_text
}

}

class Holder2(itemView: View): RecyclerView.ViewHolder(itemView){
    val commenttext = itemView.plan_text
}