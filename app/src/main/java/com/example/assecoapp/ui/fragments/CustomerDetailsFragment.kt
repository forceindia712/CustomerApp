package com.example.assecoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assecoapp.R
import com.example.assecoapp.databinding.FragmentCustomerDetailsBinding
import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.model.item.CustomerClasisfication
import com.example.assecoapp.viewmodel.CustomerViewModel

class CustomerDetailsFragment : Fragment() {

    val TAG = "CustomerDetailsFragment"

    private lateinit var binding: FragmentCustomerDetailsBinding
    private val viewModel: CustomerViewModel by activityViewModels()
    private var currentItem = Customer()
    private var currentClassificationItem = CustomerClasisfication()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUI()
        initUI()
    }

    private fun observeUI() {
        viewModel.currentItem.observe(viewLifecycleOwner) {
            currentItem = it
            initUI()
        }
        viewModel.currentClassificationItem.observe(viewLifecycleOwner) {
            currentClassificationItem = it
            initUI()
        }
    }

    private fun initUI() {
        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.action_customerDetailsFragment_to_mainFragment)
        }
        binding.name.text = currentItem.CustomerName
        binding.nip.text = currentItem.CustomerNIP
        binding.city.text = currentItem.CustomerNIP
        binding.date.text = currentItem.DateTime
        binding.classificationName.text = currentClassificationItem.ClassificationName
        binding.classificationDescription.text = currentClassificationItem.ClassificationDescription
    }
}