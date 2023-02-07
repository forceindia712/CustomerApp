package com.example.assecoapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assecoapp.R
import com.example.assecoapp.ui.adapter.CustomerAdapter
import com.example.assecoapp.databinding.FragmentMainBinding
import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.viewmodel.CustomerViewModel

class CustomerListFragment : Fragment(), CustomerAdapter.ClickListener {

    val TAG = "CustomerListFragment"

    private lateinit var binding: FragmentMainBinding
    private lateinit var rvAdapter: CustomerAdapter
    private val viewModel: CustomerViewModel by activityViewModels()
    private var customerList: ArrayList<Customer> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initUI()
    }

    private fun initList() {
        //customerList.add(Customer(0,0,"Raul", "712-35-48-78", "Zambrow", repository.dateToString()))
        viewModel.customerList.observe(viewLifecycleOwner) {
            var tempCustomerList = arrayListOf<Customer>()
            for (i in 0 until it.size) {
                if(!it.get(i).isDeleted)
                    tempCustomerList.add(it.get(i))
            }
            customerList.clear()
            customerList.addAll(tempCustomerList)
            rvAdapter.notifyDataSetChanged()
            Log.i("initList", customerList.toString())
        }
    }

    private fun initUI() {
        rvAdapter = CustomerAdapter(customerList, viewModel)
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.customerRecyclerView.setHasFixedSize(true)
        binding.customerRecyclerView.adapter = rvAdapter
        rvAdapter.setClickListener(this)

        binding.addClassificationButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_customerAddClassificationFragment)
        }
        binding.addCustomerButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_customerAddFragment)
        }
        binding.sortCustomer.setOnClickListener {
            viewModel.sortingCustomerName()
        }
        binding.sortClassification.setOnClickListener {
            viewModel.sortingClassificationName()
        }
        binding.sortDate.setOnClickListener {
            viewModel.sortingCustomerDate()
        }
    }

    override fun onItemClick(item: Customer, holder: RecyclerView.ViewHolder, position: Int) {
        viewModel.changeCurrentItem(item)
        findNavController().navigate(R.id.action_mainFragment_to_customerDetailsFragment)
    }

    override fun onDeleteClick(item: Customer, holder: RecyclerView.ViewHolder, position: Int) {
        viewModel.removeCustomerItem(item.CustomerId)
    }

}