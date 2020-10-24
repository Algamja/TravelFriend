package com.example.travelfriend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelfriend.database.model.Review
import com.example.travelfriend.ui.adapter.ReviewAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var adapter: ReviewAdapter
    private lateinit var myContext:Context

    val reviews: MutableList<Review> = mutableListOf() //data를 담아줄 list를 만들어 줌
    //fragment에서 context를 가져오기 위함
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseDatabase.getInstance().reference.child("Review") //경로로 들어감 (없으면 생성)
            .addListenerForSingleValueEvent(object : ValueEventListener {  //한번 읽고 다시 읽을 필요가 없을 때
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    reviews.clear()   //혹시 모를 상황을 위해 고정적으로 들어가줘야 함
                    for (i in dataSnapshot.children) {  //i에 child가 하나씩 들어가게됨(여기선 날짜값)
                        FirebaseDatabase
                            .getInstance()
                            .reference
                            .child("Review")
                            .child(i.key ?: "")            //Review경로 아래에 child의 key(날짜값)경로로 들어가줌
                                                                     //해당하는 key값을 몰라도 다음과 같이 쓸 수 있게된다.(key값을 모르기 떄문에 Review까지 들어감
                           .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {}

                                //Review -> 날짜 -> 그다음 값들을 넣어줌 key값을 알아서 value를 바로 쓸 수 있음
                                override fun onDataChange(snapshot: DataSnapshot) { //현재 경로(날짜) 아래에 변동사항이 있으면 캐치하여 저장
                                    val review = Review()         //변수에 class를 담아줌
                                    for (j in snapshot.children) {    //j에 child가 하나씩 들어가게된다.
                                        when (j.key) {
                                            "comment" -> {        //child가 comment일 경우
                                                review.comment = j.value as HashMap<String, String>     //value에 값이 등록이 되어 있을 경우 value변수에 값이 등록되나.
                                            }
                                            "image" -> {
                                                review.image = j.value as HashMap<String, String>
                                            }
                                            "like" -> {
                                                review.like = j.value as HashMap<String, Boolean>
                                            }
                                            "writer" -> {
                                                review.writer = j.value as String
                                            }
                                            else -> {
                                                review.number = j.value as String
                                            }
                                        }
                                    }
                                    reviews.add(review)     //data를 review 리스트에 전부 넣어줌
                                   val layoutManager = LinearLayoutManager(myContext)
                                    home_recycler_view.adapter = adapter
                                    home_recycler_view.layoutManager = layoutManager
                                    home_recycler_view.setHasFixedSize(true)
                                }

                            })

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })

        adapter = ReviewAdapter(reviews, ::onClickLike, myContext)
        return inflater.inflate(R.layout.fragment_home, container, false)   //fragment 마지막 내용
    }

    private fun setLikeToReviews(review: Review) {
        for (i in 0 until reviews.size) { //몇번쨰 이기 모르기 떄문에 size만큼 돌아야 한다.
            if (reviews[i].number == review.number) {   //몇번쨰 인지를 체크
                reviews[i].like =   //
                    mapOf(
                        FirebaseAuth.getInstance().currentUser!!.uid to !(reviews[i].like[FirebaseAuth.getInstance().currentUser!!.uid]
                            ?: false)
                    )
            }
        }
    }

    private fun onClickLike(review: Review) {
        setLikeToReviews(review)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                for (r in reviews) {
                    if (r.number == review.number) {
                        FirebaseDatabase.getInstance().reference.child("Review")
                            .child(review.number).child("like")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(r.like[FirebaseAuth.getInstance().currentUser!!.uid])
                    }
                }
            }
        }
    }
}
