package com.example.fbdictionaryfragver

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fbdictionaryfragver.databinding.FragmentBookMarkBinding
import com.example.fbdictionaryfragver.databinding.FragmentVocaBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class BookMarkFragment : Fragment() {
    lateinit var binding: FragmentBookMarkBinding

    lateinit var rdb: DatabaseReference
    lateinit var rdb2: DatabaseReference


    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyAdapter
    var findQuery = false

    lateinit var tts: TextToSpeech
    var isTtsReady = false

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
        binding = FragmentBookMarkBinding.inflate(layoutInflater,container,false)
        initTTS()
        initFB()
        return binding!!.root
    }
    private fun initTTS(){
        tts = TextToSpeech(context, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    fun initFB() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val query = rdb.limitToLast(100000)
        val option = FirebaseRecyclerOptions.Builder<MyData>()
            .setQuery(query, MyData::class.java)
            .build()
        adapter = MyAdapter(option)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = adapter


        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {

            override fun OnItemClick(view: View, position: Int) {

            }

        }
        adapter.itemClickListener3 = object:MyAdapter.OnItemClickListener3{
            override fun OnItemClick(position: Int, holder: MyAdapter.ViewHolder) {
                if(isTtsReady)
                    tts.speak(holder.binding.word.text,TextToSpeech.QUEUE_ADD, null, null)
                Toast.makeText(context, holder.binding.meaning.text, Toast.LENGTH_SHORT).show()
                //(data.meaning)

            }


        }

        adapter.itemClickListener2 = object:MyAdapter.OnItemClickListener2{
            override fun OnItemClick(
                position: Int,
                checked: Boolean,
                holder: MyAdapter.ViewHolder
            ) {
                if(checked){
                    rdb2.child(holder.binding.word.text.toString())
                        .child("expanded")
                        .setValue(true)
                    var word = holder.binding.word.text.toString()
                    var meaning = holder.binding.meaning.text.toString()
                    var item2 = MyData(word,meaning,true)
                    rdb.child(word.toString()).setValue(item2)

                }
                else{
                    rdb2.child(holder.binding.word.text.toString())
                        .child("expanded")
                        .setValue(false)
                    rdb.child(holder.binding.word.text.toString()).removeValue()
                }
            }

        }
        adapter.startListening()

    }
}