package com.example.fbdictionaryfragver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fbdictionaryfragver.databinding.QuizRowBinding
import com.example.fbdictionaryfragver.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class QuizAdapter(options: FirebaseRecyclerOptions<MyData>)
    : FirebaseRecyclerAdapter<MyData, QuizAdapter.ViewHolder>(options) {



    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null

    interface OnItemClickListener2{
        fun OnItemClick(view: View, position: Int,holder: ViewHolder)
    }
    var itemClickListener2:OnItemClickListener2?=null


    inner class ViewHolder(val binding: QuizRowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemClickListener!!.OnItemClick(it, adapterPosition)

            }

            binding.button2.setOnClickListener {
                itemClickListener2!!.OnItemClick(it, adapterPosition,this)

            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = QuizRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: MyData) {
        holder.binding.apply {

            quizmeaning.text = model.meaning.toString()
            quizword.text = model.word.toString()



        }
    }

}