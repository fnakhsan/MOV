package com.fnakhsan.mov.dashboard.home

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fnakhsan.mov.R
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.dashboard.home.detail.MovieDetailActivity
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.data.User
import com.fnakhsan.mov.databinding.FragmentHomeBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mUserRef: DatabaseReference
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

        val username = preferences.getValues("user")
        mUserRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User").child("$username")

        homeBinding.apply {
            tvName.text = username
            val balance = preferences.getValues("saldo")
            if (!balance.equals("")) {
                currency(balance!!.toDouble(), tvBalance)
            }
            val npLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvNp.layoutManager = npLayoutManager
            rvNp.addItemDecoration(DividerItemDecoration(context, npLayoutManager.orientation))
            val csLayoutManager = LinearLayoutManager(context)
            rvCs.layoutManager = csLayoutManager
            rvCs.addItemDecoration(DividerItemDecoration(context, csLayoutManager.orientation))
        }

        Log.d(TAG, preferences.getValues("url").toString())
        val getPhoto = preferences.getValues("url")
        Log.d(TAG, (getPhoto == String()).toString())
        if (getPhoto == String()) {
            addPhoto(getPhoto)
        } else {
            mUserRef.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = it.getValue(User::class.java)
                    Log.d(TAG, user.toString())
                    addPhoto(user?.url)
                    Log.d(TAG, user?.url.toString())
                } else {
                    homeBinding.imgProfile.setImageResource(R.drawable.user_pic)
                }
            }.addOnFailureListener{
                Log.w(TAG, "Failed to read value.", it)
            }
        }
        getData()
    }

    private fun addPhoto(url: String?) {
        Glide.with(homeBinding.imgProfile)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(homeBinding.imgProfile)
    }

    private fun currency(balance: Double, tvBalance: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        tvBalance.text = format.format(balance)
    }

    private fun getData() {
        mDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (getSnapshot in snapshot.children) {
                    val film = getSnapshot.getValue(Film::class.java)
                    filmList.add(film!!)
                }

                with(homeBinding) {
                    val adapterNP = NowPlayingAdapter(filmList)
                    rvNp.adapter = adapterNP
                    adapterNP.setOnItemCLickCallback(object :
                        NowPlayingAdapter.OnItemClickCallback {
                        override fun onItemClicked(film: Film) {
                            val intent = Intent(context, MovieDetailActivity::class.java).putExtra(
                                "film",
                                film
                            )
                            startActivity(intent)
                        }
                    })

                    val adapterCS = ComingSoonAdapter(filmList)
                    rvCs.adapter = adapterCS
                    adapterCS.setOnItemCLickCallback(object :
                        ComingSoonAdapter.OnItemClickCallback {
                        override fun onItemClicked(film: Film) {
                            val intent = Intent(context, MovieDetailActivity::class.java).putExtra(
                                "film",
                                film
                            )
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}