package com.example.travelfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up_user_basic.*

class SignUpUserBasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_basic)

        val input_name = sign_up_name_input.text.toString()
        val input_phone = sign_up_phone_number_input.text.toString()

        sign_up_second_next_button.setOnClickListener(){
            val intent = Intent(this,Sign_up_user_detail::class.java)
            intent.putExtra("name",input_name)
            intent.putExtra("phone",input_phone)
        startActivity(intent)
        }
    }
}
