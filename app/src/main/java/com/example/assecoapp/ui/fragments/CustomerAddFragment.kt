package com.example.assecoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assecoapp.R
import com.example.assecoapp.databinding.FragmentCustomerAddBinding
import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.viewmodel.CustomerViewModel

class CustomerAddFragment : Fragment() {

    val TAG = "CustomerAddFragment"

    private lateinit var binding: FragmentCustomerAddBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        var adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, viewModel.getClassificationName()) }
        binding.classificationSpinner.adapter = adapter

        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.action_customerAddFragment_to_mainFragment)
        }
        binding.addCustomerButton.setOnClickListener {
            if(!checkAllFields())
                if(binding.classificationSpinner.isEmpty())
                    Toast.makeText(context, R.string.no_classifier, Toast.LENGTH_SHORT).show()
                else if(binding.nipEdittext.length() != 10)
                    Toast.makeText(context, R.string.nip_10_numbers, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, R.string.too_short, Toast.LENGTH_SHORT).show()
            else {
                val customerItem = Customer(
                    1,
                    binding.classificationSpinner.selectedItemId,
                    binding.nameEdittext.text.toString(),
                    binding.nipEdittext.text.toString(),
                    binding.cityEdittext.text.toString(),
                    "0"
                )
                viewModel.addCustomerItem(customerItem)
                findNavController().navigate(R.id.action_customerAddFragment_to_mainFragment)
            }
        }
    }

    private fun checkAllFields(): Boolean {
        return binding.nameEdittext.length() > 0 &&
                binding.nipEdittext.length() == 10 &&
                binding.cityEdittext.length() > 0 &&
                !binding.classificationSpinner.isEmpty()
    }
}