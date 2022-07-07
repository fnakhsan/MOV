package com.fnakhsan.mov.dashboard.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fnakhsan.mov.databinding.FragmentHomeBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var preferences: Preferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity!!.applicationContext)

        mDatabaseRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film")

        with(homeBinding){
            tvName.text = preferences.getValues("user")
        }
    }
}