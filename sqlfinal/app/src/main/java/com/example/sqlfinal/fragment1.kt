package com.example.sqlfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.sqlfinal.databinding.ActivityMainBinding
import com.example.sqlfinal.databinding.FragmentFragment1Binding


class fragment1 : Fragment() {
    lateinit var binding: FragmentFragment1Binding
    lateinit var myDBHelper: MyDBHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFragment1Binding.inflate(layoutInflater, container, false)
        return binding.root
//        initDB()
        init()
        getAllRecord()
    }

//    private fun initDB() {
//        val dbfile = requireContext().getDatabasePath("mydb.db")
//        if(dbfile.parentFile.exists()){
//            dbfile.parentFile.mkdir()
//        }
//        if(!dbfile.exists()){
//            val file = resources.openRawResource(R.raw.mydb)
//            val fileSize = file.available()
//            val buffer = ByteArray(fileSize)
//            file.read(buffer)
//            file.close()
//            dbfile.createNewFile()
//            val output = FileOutputStream(dbfile)
//            output.write(buffer)
//            output.close()
//        }
//    }

    fun getAllRecord(){
        myDBHelper.getAllRecord()
    }
    fun clearEditText(){
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }
    private fun init() {
        myDBHelper = MyDBHelper(context)
        binding.apply {
            testsql.addTextChangedListener {
                val pname = it.toString()
                val result = myDBHelper.findProduct2(pname)
            }
            insertbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(0, name, quantity)
                val result = myDBHelper.insertProduct(product)
                if(result){
                    getAllRecord()
                    Toast.makeText(context, "Data INSERT SUCCESS", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "Data INSERT FAIL", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }
            findbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val result = myDBHelper.findProduct(name)
                if(result){
                    getAllRecord()
                    Toast.makeText(context, "RECORD FOUND", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "NO MATCH FOUND", Toast.LENGTH_SHORT).show()
                }
            }
            deletebtn.setOnClickListener {
                val pid = pIdEdit.text.toString()
                val result = myDBHelper.deleteProduct(pid)
                if(result){
                    Toast.makeText(context, "DELETE SUCCESS", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "DELETE FAIL", Toast.LENGTH_SHORT).show()
                }
                getAllRecord()
                clearEditText()
            }
            updatebtn.setOnClickListener {
                val pid = pIdEdit.text.toString().toInt()
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(pid, name, quantity)
                val result = myDBHelper.updateProduct(product)
                if(result){
                    getAllRecord()
                    Toast.makeText(context, "UPDATE SUCCESS", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "UPDATE FAIL", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }
        }
    }
}