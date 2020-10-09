package com.example.travelfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up_email_and_password.*

class SignUpEmailAndPasswordActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var GOOGLE_LOGIN_CODE = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_email_and_password)

        auth = FirebaseAuth.getInstance()

        sign_up_first_next_button.setOnClickListener(){
            val email = sign_up_email_input.text.toString()
            val pw1 = sign_up_password_input.text.toString()
            val pw2 = sign_up_password_check_input.text.toString()
            if(pw1!=pw2){
                Toast.makeText(this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show()
            }
            else if(email.isEmpty() || pw1.isEmpty()){
                Toast.makeText(this, "이메일 또는 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                createEmail()
            }
        }


    }
    fun createEmail(){
        auth?.createUserWithEmailAndPassword(sign_up_email_input.text.toString(),sign_up_password_input.text.toString())
            ?.addOnCompleteListener {

                if(it.isSuccessful) {
                    startActivity(Intent(this, SignUpUserBasicActivity::class.java))
                }else if (!it.exception.toString().isNullOrEmpty()){
                    Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                }
            }

    }
}
