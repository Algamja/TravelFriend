package com.example.travelfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.example.travelfriend.database.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up_user_detail.*
import kotlinx.android.synthetic.main.activity_sign_up_user_detail.view.*

class SignUpUserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_detail)

        var gender= ""
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

        sign_up_done.setOnClickListener {
            val nickname = sign_up_nickname_input.text.toString()
            val age = sign_up_age_input.text.toString()
            val email = intent.getStringExtra("email")?:""
            val name = intent.getStringExtra("name")?:""
            val phone = intent.getStringExtra("phone")?:""
            val uid = intent.getStringExtra("uid")?:""

            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("User").child(uid)
            val dataclass = User(email, nickname, name, age, gender, phone)
            database.setValue(dataclass)
            //User경로 아래에 dataclass 형태로 데이터를 넣어준다.(db에 저장)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
