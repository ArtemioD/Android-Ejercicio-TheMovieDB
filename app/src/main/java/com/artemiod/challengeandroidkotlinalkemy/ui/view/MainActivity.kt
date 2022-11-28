package com.artemiod.challengeandroidkotlinalkemy.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artemiod.challengeandroidkotlinalkemy.R
import com.artemiod.challengeandroidkotlinalkemy.domain.SelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSelected(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id", id)

        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

}