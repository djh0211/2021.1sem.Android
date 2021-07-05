package com.example.mydictionary

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.mydictionary.placeholder.PlaceholderContent.PlaceholderItem
import com.example.mydictionary.databinding.Fragment0Binding


class MyItemRecyclerViewAdapter(
    val items: List<MyData>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            Fragment0Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val mydata : MyData = items[position]
        holder.wordTxt.text = mydata.word
        holder.meaningTxt.text = mydata.meaning

        val isExpandable: Boolean = items[position].isExpanded
        holder.expandablelayout.visibility = if(isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val mydata = items[position]
            mydata.isExpanded = !mydata.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: Fragment0Binding) : RecyclerView.ViewHolder(binding.root) {
        var wordTxt: TextView = binding.word
        val meaningTxt: TextView = binding.meaning
        var linearLayout: LinearLayout = binding.linearLayout
        var expandablelayout: RelativeLayout = binding.expandableLayout

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}