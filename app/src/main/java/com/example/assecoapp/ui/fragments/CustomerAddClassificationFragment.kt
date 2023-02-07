package com.example.assecoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assecoapp.R
import com.example.assecoapp.databinding.FragmentCustomerClassificationAddBinding
import com.example.assecoapp.model.item.CustomerClasisfication
import com.example.assecoapp.viewmodel.CustomerViewModel

class CustomerAddClassificationFragment : Fragment() {

    val TAG = "CustomerAddClassificationFragment"

    private lateinit var binding: FragmentCustomerClassificationAddBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerClassificationAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.action_customerAddClassificationFragment_to_mainFragment)
        }
        binding.addCustomerClassificationButton.setOnClickListener {
            if (!checkAllFields())
                Toast.makeText(context, R.string.too_short, Toast.LENGTH_SHORT).show()
            else {
                val customerClassificationItem = CustomerClasisfication(
                    1,
                    binding.nameEdittext.text.toString(),
                    binding.descriptionEdittext.text.toString()
                )
                viewModel.addCustomerClassificationItem(customerClassificationItem)
                findNavController().navigate(R.id.action_customerAddClassificationFragment_to_mainFragment)
            }
        }
    }

    private fun checkAllFields(): Boolean {
        return binding.nameEdittext.length() > 0 &&
                binding.descriptionEdittext.length() > 0
    }
}