package com.example.travelfriend

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.Plan
import com.example.travelfriend.ui.adapter.PlanAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_plan.view.*

/**
 * A simple [Fragment] subclass.
 */
class PlanFragment : Fragment() {
    lateinit var post: Plan
    lateinit var myContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plan, container, false)
        view.p_button.setOnClickListener {
            val comment2 = p_edit.text.toString()
            val input1 = Plan(comment2)
            FirebaseDatabase.getInstance().getReference("Plan")
                .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(input1)

            val database1 = FirebaseDatabase.getInstance().getReference("Plan")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
            database1.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    post = dataSnapshot.getValue(Plan::class.java) ?: Plan()
                    //데이터를 클래스 형태로 가져와줌
                    var data: MutableList<Plan> = setData()
                    var adapter = PlanAdapter(myContext)
                    adapter.listData = data
                    p_recylcerview.adapter = adapter
                    p_recylcerview.layoutManager = LinearLayoutManager(myContext)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
        return view
    }

    fun setData(): MutableList<Plan> {
        var data: MutableList<Plan> = mutableListOf()
        var uiddata = post
        data.add(uiddata)
        return data
    }
}
