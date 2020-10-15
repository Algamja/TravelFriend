package com.example.travelfriend.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelfriend.R
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.util.LikeCompare
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.item_home.view.*
import kotlin.reflect.KFunction1


class ReviewAdapter(
    var reviews: List<Review>,
    val likeClick: KFunction1<@ParameterName(name = "review") Review, Unit>,
    val commentClick: () -> Unit,
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewViewHolder).bind(reviews[position])
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.item_home_image_carousel_view
        private val like = itemView.item_home_like_radio_button
        private val commentImg = itemView.item_home_comment_image
        private val commentText = itemView.item_home_comment_text

        fun bind(review: Review) {
            val imageListener =
                ImageListener { position, imageView ->
                    val image: Array<String> = review.image.values.toTypedArray()
                    Glide.with(context).load(image[position]).into(imageView)
                }

            image.pageCount = review.image.size
            image.setImageListener(imageListener)

            like.isChecked = false
            if (LikeCompare().likeCompare(review)) {
                like.isChecked = true
            }


            commentText.text = review.comment.values.toTypedArray()[review.comment.size - 1]

            like.setOnClickListener(View.OnClickListener { likeClick(review) })
            commentImg.setOnClickListener(View.OnClickListener { commentClick() })
            commentText.setOnClickListener(View.OnClickListener { commentClick() })
        }
    }
}