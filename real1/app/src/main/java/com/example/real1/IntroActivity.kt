package com.example.real1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class IntroActivity : AppCompatActivity() {
    val ADDVOC_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }
    private fun init() {

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.a_addbtn)
        val button4 = findViewById<Button>(R.id.a_cancelbtn)


        button.setOnClickListener{  // 첫번째 버튼을 누르면
            val i = Intent(this, ChooseTypeActivity::class.java) //단어장으로 넘어가야되니까 인텐트 생성 this여기서 mainact의 클래스로 이동
            startActivity(i)// intent를 받아서 액티비티 시작
        }
        button2.setOnClickListener{ // 두번째 버튼을 누르면
            val i = Intent(this, AddVocActivity::class.java)
            startActivityForResult(i, ADDVOC_REQUEST)
        }
        button3.setOnClickListener{// 세번째 버튼을 누르면
            val i = Intent(this, MyOwnQuizActivity::class.java)
            startActivity(i)
        }
        button4.setOnClickListener{
            val i = Intent(this, VocabActivity::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ADD_VOC_REQUEST ->{
                if(resultCode== Activity.RESULT_OK){
                    val str = data?.getSerializableExtra("voc") as MyData
                    Toast.makeText(this, str.word+"단어 추가됨", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}