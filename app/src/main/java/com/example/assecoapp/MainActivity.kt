package com.example.assecoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.assecoapp.viewmodel.CustomerViewModel
import com.example.assecoapp.viewmodel.CustomerViewModelFactory

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private lateinit var viewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, CustomerViewModelFactory(applicationContext))[CustomerViewModel::class.java]
    }
}