package com.example.fbdictionaryfragver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.fbdictionaryfragver.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyAdapter(options: FirebaseRecyclerOptions<MyData>)
    : FirebaseRecyclerAdapter<MyData, MyAdapter.ViewHolder>(options) {



    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null

    var itemClickListener2:OnItemClickListener2?=null
    interface OnItemClickListener2{
        fun OnItemClick(position: Int, checked:Boolean, holder: ViewHolder)
    }
    var itemClickListener3:OnItemClickListener3?=null
    interface OnItemClickListener3{
        fun OnItemClick(position: Int, holder: ViewHolder)
    }





    inner class ViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemClickListener!!.OnItemClick(it, adapterPosition)

            }
            binding.linearLayout.setOnClickListener {
                if(binding.linearLayout2.visibility == View.VISIBLE){
                    binding.linearLayout2.visibility = View.GONE
                }
                else
                    binding.linearLayout2.visibility = View.VISIBLE
            }
            binding.button.setOnClickListener {
                itemClickListener3!!.OnItemClick(adapterPosition, this)
            }
            binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
                itemClickListener2!!.OnItemClick(adapterPosition, isChecked, this)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: MyData) {
        holder.binding.apply {


            word.text = model.word.toString()
            meaning.text = model.meaning.toString()
            switch1.isChecked = model.isElxpanded


        }
    }

}

