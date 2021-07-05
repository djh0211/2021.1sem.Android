package com.example.fbdictionaryfragver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fbdictionaryfragver.databinding.FragmentInternetBinding

class InternetFragment : Fragment() {
    lateinit var binding:FragmentInternetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInternetBinding.inflate(layoutInflater,container,false)
        init()
        return binding.root
    }

    fun init(){
        binding.apply {
            oxfordli.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.oxfordlearnersdictionaries.com/"))
                startActivity(intent)

            }
            naverli.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.dict.naver.com/#/main"))
                startActivity(intent)

            }
            daumli.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://dic.daum.net/"))
                startActivity(intent)

            }
        }
    }

}