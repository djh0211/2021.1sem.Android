package com.example.fbdictionaryfragver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.fbdictionaryfragver.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.apply {
            vocali.setOnClickListener {
                From_Home_Transaction(VocaFragment())

            }
            addli.setOnClickListener {
                From_Home_Transaction(AddFragment())

            }
            bookmarkli.setOnClickListener {
                From_Home_Transaction(BookMarkFragment())

            }
            quizli.setOnClickListener {
                From_Home_Transaction(QuizFragment())

            }
            internetli.setOnClickListener {
                From_Home_Transaction(InternetFragment())

            }
        }

        return binding.root
    }
    fun From_Home_Transaction(fg:Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameLayout, fg)
        transaction?.addToBackStack(null)
        transaction?.commit()
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }


}