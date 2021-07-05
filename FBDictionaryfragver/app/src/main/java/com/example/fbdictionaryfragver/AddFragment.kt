package com.example.fbdictionaryfragver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fbdictionaryfragver.databinding.FragmentAddBinding
import com.example.fbdictionaryfragver.databinding.FragmentVocaBinding
import com.example.fbdictionaryfragver.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding

    lateinit var rdb: DatabaseReference
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyAdapter
    var findQuery = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        initFB()
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rdb = FirebaseDatabase.getInstance().getReference("MyData/items")
    }



    fun initFB() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<MyData>()
            .setQuery(query, MyData::class.java)
            .build()
        adapter = MyAdapter(option)
        adapter.itemClickListener = object :MyAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {
                binding.apply {
                    wordEdit.setText(adapter.getItem(position).word.toString())
                    meaningEdit.setText(adapter.getItem(position).meaning.toString())

                }
            }

        }
        binding.apply{
            insertbtn.setOnClickListener {
                val item = MyData(wordEdit.text.toString(),
                    meaningEdit.text.toString(),false)
                rdb.child(wordEdit.text.toString()).setValue(item)
                Toast.makeText(context, "단어가 추가됐습니다. ", Toast.LENGTH_SHORT).show()
                clearInput()
            }
            deletebtn.setOnClickListener {
                rdb.child(wordEdit.text.toString()).removeValue()
                Toast.makeText(context, "단어가 삭제됐습니다. ", Toast.LENGTH_SHORT).show()
                clearInput()
            }

        }

        adapter.startListening()

    }
    fun clearInput(){
        binding.apply{
            wordEdit.text.clear()
            meaningEdit.text.clear()


        }
    }




}