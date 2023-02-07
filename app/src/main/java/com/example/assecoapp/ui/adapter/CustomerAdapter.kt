package com.example.assecoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assecoapp.databinding.ItemCustomerBinding
import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.viewmodel.CustomerViewModel

class CustomerAdapter(private var list: ArrayList<Customer>, private var viewModel: CustomerViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG = "CustomerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingNormal =
            ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(bindingNormal)
    }

    private inner class MyViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val containerView: View
            get() = binding.root
    }

    private fun CustomerItem(viewHolder: MyViewHolder, position: Int) {
        val customerItem = list[position]
        viewHolder.binding.customerCity.text = customerItem.CustomerCity
        viewHolder.binding.classificationName.text = viewModel.getSingleClassificationName(customerItem.ClassificationId)
        viewHolder.binding.customerName.text = customerItem.CustomerName
        viewHolder.binding.customerDate.text = customerItem.DateTime
        viewHolder.binding.customerNip.text = customerItem.CustomerNIP
        viewHolder.binding.BOTTOMEND.setOnClickListener {
            clickListener?.onDeleteClick(customerItem, viewHolder, position)
        }
        viewHolder.binding.customerName.setOnClickListener {
            clickListener?.onItemClick(customerItem, viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return CustomerItem(holder as MyViewHolder, position)
    }

    interface ClickListener {
        fun onDeleteClick(item: Customer, holder: RecyclerView.ViewHolder, position: Int)
        fun onItemClick(item: Customer, holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    private var clickListener: ClickListener? = null
}