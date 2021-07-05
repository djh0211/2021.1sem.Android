package com.example.myfbtest

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfbtest.databinding.FragmentSettingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SettingFragment : Fragment() {
    val firestore = FirebaseFirestore.getInstance()
    var binding: FragmentSettingBinding? = null

    lateinit var rdb: DatabaseReference


    var TestInfo_ArrayList : ArrayList<TestInfo> = ArrayList()

    lateinit var dialogView:View
    lateinit var adapter: SettingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        dialogView = inflater.inflate(R.layout.add_dialog, container, false)

        init()

        return binding!!.root


    }



    private fun init() {
        rdb = FirebaseDatabase.getInstance().getReference("datas/items")


        binding!!.recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        adapter = SettingAdapter(TestInfo_ArrayList)
        binding!!.recyclerView.adapter = adapter


        binding!!.addBtn2.setOnClickListener {
            val mBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .setTitle("단어 추가")
            val mAlertDialog = mBuilder.show()
            val okButton = dialogView.findViewById<Button>(R.id.addDialogAddButton)
            val noButton = dialogView.findViewById<Button>(R.id.addDialogCancleButton)
            okButton.setOnClickListener {
                val testName = dialogView.findViewById<EditText>(R.id.addDialogCookingName).text.toString()
                val testTime= dialogView.findViewById<EditText>(R.id.addDialogCookingTime).text.toString()

                val test = TestInfo(testName,testTime,"",String.format("%02d", testTime.toInt()/60),String.format("%02d", testTime.toInt()%60),"00")
                TestInfo_ArrayList.add(test)
                adapter.notifyDataSetChanged()

                var newTest = hashMapOf(
                    "testName" to testName,
                    "testTime" to testTime,
                    "hour" to String.format("%02d", testTime.toInt()/60),
                    "minute" to String.format("%02d", testTime.toInt()%60),
                    "sec" to "00"
                )

                val testTitle = binding!!.addText.text.toString()
                val date = binding!!.addText2.text.toString()
                rdb.child(testTitle).setValue(TestTitle(testTitle,date))

                firestore.collection(testTitle).document(testName).set(newTest)

            }
            noButton.setOnClickListener {

                mAlertDialog.dismiss()
                //(dialogView.parent as ViewGroup).removeView(dialogView)
            }
        }

        binding!!.button3.setOnClickListener {
            val fragment = activity!!.supportFragmentManager.beginTransaction()
            //fragment.addToBackStack(null)
            fragment.replace(R.id.frame, ItemFragment2())
            fragment.commit()
        }


    }


}