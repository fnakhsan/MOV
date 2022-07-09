package com.fnakhsan.mov.dashboard.home

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.databinding.FragmentHomeBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var preferences: Preferences
    private var filmList = ArrayList<Film>()
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

        with(homeBinding) {
            tvName.text = preferences.getValues("user")
            val balance = preferences.getValues("saldo")
            if (balance == "") {
                val localID = Locale("in", "ID")
                val format = NumberFormat.getCurrencyInstance(localID)
                tvBalance.text = format.format(balance.toDouble())
            }
            val npLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNp.layoutManager = npLayoutManager
            rvNp.addItemDecoration(DividerItemDecoration(context, npLayoutManager.orientation))
            val csLayoutManager = LinearLayoutManager(context)
            rvCs.layoutManager = csLayoutManager
            rvCs.addItemDecoration(DividerItemDecoration(context, csLayoutManager.orientation))

        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(homeBinding.imgProfile)

        getData()
    }

    private fun getData() {
        mDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (getSnapshot in snapshot.children){
                    val film = getSnapshot.getValue(Film::class.java)
                    filmList.add(film!!)
                }

                with(homeBinding){
                    rvNp.adapter = NowPlayingAdapter(filmList)
//                    rvCs.adapter = ComingSoonAdapter(filmList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}