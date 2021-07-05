package com.example.fbdictionaryfragver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fbdictionaryfragver.databinding.FragmentBookMarkBinding
import com.example.fbdictionaryfragver.databinding.FragmentQuizBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QuizFragment : Fragment() {
    lateinit var binding: FragmentQuizBinding

    lateinit var rdb: DatabaseReference
    lateinit var rdb2: DatabaseReference


    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: QuizAdapter
    var findQuery = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rdb = FirebaseDatabase.getInstance().getReference("MyData/bookmark")
        rdb2 = FirebaseDatabase.getInstance().getReference("MyData/items")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(layoutInflater,container,false)
        initFB()
        return binding!!.root
    }

    fun initFB() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val query = rdb2.limitToLast(100000)
        val option = FirebaseRecyclerOptions.Builder<MyData>()
            .setQuery(query, MyData::class.java)
            .build()
        adapter = QuizAdapter(option)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = adapter


        adapter.itemClickListener = object : QuizAdapter.OnItemClickListener {

            override fun OnItemClick(view: View, position: Int) {

            }

        }
        adapter.itemClickListener2 = object:QuizAdapter.OnItemClickListener2{
            override fun OnItemClick(view: View, position: Int, holder: QuizAdapter.ViewHolder) {
                if (holder.binding.answerEdit.text.toString()==holder.binding.quizword.text.toString()){
                    Toast.makeText(context, "정답입니다.", Toast.LENGTH_SHORT).show()
                    holder.binding.answerEdit.text.clear()
                }
                else{
                    Toast.makeText(context, "틀렸습니다.", Toast.LENGTH_SHORT).show()
                    rdb2.child(holder.binding.quizword.text.toString())
                        .child("expanded")
                        .setValue(true)
                    holder.binding.answerEdit.text.clear()

                }

            }

        }
        adapter.startListening()

    }


}


