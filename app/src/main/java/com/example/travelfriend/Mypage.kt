package com.example.travelfriend

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.travelfriend.database.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mypage.*

class Mypage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)


        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("User").child(FirebaseAuth.getInstance().currentUser!!.uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post: User? = dataSnapshot.getValue<User>(User::class.java)
                if (post != null) {
                    Glide.with(this@Mypage).load(post.image)
                        .apply(RequestOptions.overrideOf(600, 600))
                        .apply(RequestOptions.centerCropTransform())
                        .into(mypage_image)

                    mypage_email.setText(post.email)
                    mypage_nickname.setText(post.nickname)
                    mypage_name.setText(post.name)
                    mypage_phone.setText(post.phonenumber)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
