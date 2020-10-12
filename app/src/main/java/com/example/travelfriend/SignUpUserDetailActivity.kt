package com.example.travelfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_sign_up_user_detail.*

class SignUpUserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_user_detail)

        val nickname = sign_up_nickname_input.text.toString()
        val age = sign_up_age_input.text.toString()
        val chk_gender = sign_up_gender_group.getCheckedRadioButtonId()
        val checkedRadioButton = findViewById<RadioButton>(chk_gender)
        val checkBox = checkedRadioButton.getText().toString()

        val email1 = intent.getStringExtra("email")
        val pw1 = intent.getStringExtra("pw")
        sign_up_done.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nickname", nickname)
            intent.putExtra("age", age)
            intent.putExtra("gender", checkBox)
            intent.putExtra("email",email1)
            intent.putExtra("pw",pw1)
            startActivity(intent)
        }
    }
}
