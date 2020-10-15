package com.example.travelfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up_user_detail.*
import kotlinx.android.synthetic.main.activity_sign_up_user_detail.view.*

class SignUpUserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_detail)



        sign_up_done.setOnClickListener {
            var gender : String ="미정"
            sign_up_gender_group.setOnCheckedChangeListener(){radioGroup,checkedId->
                when(checkedId){
                    R.id.sign_up_gender_male ->{
                        gender = "남"
                    }
                    R.id.sign_up_gender_female->{
                        gender = "여"
                    }
                }
            }

                    //Write a message to the database
            val nickname = sign_up_nickname_input.text.toString()
            val age = sign_up_age_input.text.toString()



            val email1 = intent.getStringExtra("email")
            val pw1 = intent.getStringExtra("pw")
            val name = intent.getStringExtra("name")
            var phone = intent.getStringExtra("phone")

            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("data")
            val dataclass = data1(name,phone,nickname,age,gender,email1,pw1)
            myRef.setValue(dataclass)

            val intent = Intent(this, HomeActivity::class.java)
            //Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }

                //변동되는 순간 찍어줌
                override fun onDataChange(snapshot: DataSnapshot) {
                    //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다.
                    val value = snapshot?.value
                }
            })
            startActivity(intent)
        }

    }
}

data class data1(val name:String, val phone:String, val nickname1: String, val age1: String, val chekbox1:String, val email1:String, val pw1:String)

