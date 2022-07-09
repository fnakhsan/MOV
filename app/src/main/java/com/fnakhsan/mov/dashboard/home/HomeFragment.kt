package com.fnakhsan.mov.dashboard.home

import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
        Log.d(TAG, "pref")
        preferences = Preferences(activity!!.applicationContext)
        Log.d(TAG, "conn")
        mDatabaseRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film")

        with(homeBinding) {
            tvName.text = preferences.getValues("user")
            val balance = preferences.getValues("saldo")
            Log.d(TAG, "getPref")
            if (!balance.equals("")) {
                currency(balance!!.toDouble(), tvBalance)
            }
            Log.d(TAG, "balance")
            val npLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNp.layoutManager = npLayoutManager
            rvNp.addItemDecoration(DividerItemDecoration(context, npLayoutManager.orientation))
            Log.d(TAG, "np")
            val csLayoutManager = LinearLayoutManager(context)
            rvCs.layoutManager = csLayoutManager
            rvCs.addItemDecoration(DividerItemDecoration(context, csLayoutManager.orientation))
            Log.d(TAG, "cs")

        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(homeBinding.imgProfile)
        Log.d(TAG, "pp")
        getData()
    }

    private fun currency(balance: Double, tvBalance: TextView) {
        Log.d(TAG, "1")
        val localID = Locale("in", "ID")
        Log.d(TAG, "2")
        val format = NumberFormat.getCurrencyInstance(localID)
        Log.d(TAG, "3")
        tvBalance.text = format.format(balance)
        Log.d(TAG, "4")
    }

    private fun getData() {
        mDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (getSnapshot in snapshot.children){
                    val film = getSnapshot.getValue(Film::class.java)
                    filmList.add(film!!)
                }
                Log.d(TAG, "$filmList")

                with(homeBinding){
                    rvNp.adapter = NowPlayingAdapter(filmList)
                    Log.d(TAG, "npAdapter")
                    rvCs.adapter = ComingSoonAdapter(filmList)
                    Log.d(TAG, "csAdapter")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    companion object {
        const val TAG = "Home"
    }

}