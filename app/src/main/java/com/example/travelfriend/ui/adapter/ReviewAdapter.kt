package com.example.travelfriend.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelfriend.R
import com.example.travelfriend.database.model.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.item_home.view.*


class ReviewAdapter(
    val likeClick: (Review) -> Unit,
    val commentClick: (Review) -> Unit,
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var reviews: MutableList<Review> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewViewHolder).bind(reviews[position])
    }

    fun setReviews(reviews: MutableList<Review>) {
        this.reviews = reviews
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.item_home_image_carousel_view
        private val like = itemView.item_home_like_radio_button
        private val commentImg = itemView.item_home_comment_image
        private val commentText = itemView.item_home_comment_text


        fun bind(review: Review) {
            val imageListener =
                ImageListener { position, imageView ->
                    Glide.with(context).load(review.image[position]).into(imageView)
                }

            image.pageCount = review.image.size
            image.setImageListener(imageListener)

            val myUID = FirebaseAuth.getInstance().currentUser!!.uid
            like.isSelected = false
            for (i in review.like) {
                if (myUID == i.writer) {
                    like.isSelected = true
                    break
                }
            }

            commentText.text = review.comment[review.comment.size - 1].comment

            like.setOnClickListener(View.OnClickListener { likeClick(review) })
            commentImg.setOnClickListener(View.OnClickListener { commentClick(review) })
            commentText.setOnClickListener(View.OnClickListener { commentClick(review) })
        }
    }
}