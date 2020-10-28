package com.example.travelfriend


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {

    lateinit var myContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        view.setting_mypage.setOnClickListener(){
            val intent = Intent(context,Mypage::class.java)
            startActivity(intent)
        }
            return view
    }

}