package com.example.assecoapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assecoapp.model.db.CustomersDbHelper
import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.model.item.CustomerClasisfication
import com.example.assecoapp.model.repository.CustomerRepository

class CustomerViewModel(context: Context) : ViewModel() {

    val TAG = "CustomerViewModel"

    var repository = CustomerRepository()
    var customerDB = CustomersDbHelper(context)
    var customerList: MutableLiveData<ArrayList<Customer>> = MutableLiveData(arrayListOf())
    var customerClassificationList: MutableLiveData<ArrayList<CustomerClasisfication>> = MutableLiveData(arrayListOf())
    var currentItem: MutableLiveData<Customer> = MutableLiveData()
    var currentClassificationItem: MutableLiveData<CustomerClasisfication> = MutableLiveData()

    init {
        customerList.postValue(customerDB.readCustomersArray())
        customerClassificationList.postValue(customerDB.readCustomersClassificationArray())
    }

    fun addCustomerItem(customerItem: Customer) {
        if (customerList.value?.size?.toLong() != null)
            customerItem.CustomerId = customerList.value?.size?.toLong()!!
        customerItem.DateTime = repository.dateToString()
        customerList.value?.add(customerItem)
        customerList.postValue(customerList.value)

        customerDB.addCustomer(customerItem)
    }

    fun removeCustomerItem(customerId: Long) {
        for (i in 0 until customerList.value?.size!!) {
            if(customerList.value!!.get(i).CustomerId == customerId)
                customerList.value?.get(i)?.isDeleted = true
        }
        customerList.postValue(customerList.value)

        customerDB.updateCustomers(customerId, repository.dateToString())
    }

    fun addCustomerClassificationItem(customerClassificationItem: CustomerClasisfication) {
        customerClassificationItem.ClassificationId =
            customerClassificationList.value?.size?.toLong()!!
        customerClassificationList.value?.add(customerClassificationItem)

        customerDB.addCustomerClassification(customerClassificationItem)
    }

    fun sortingCustomerName() {
        customerList.postValue(customerList.value?.let { repository.sortingCustomerName(it) })
    }

    fun sortingClassificationName() {
        customerList.postValue(customerList.value?.let { customerList ->
            customerClassificationList.value?.let { customerClassification ->
                repository.sortingClassificationName(
                    customerClassification,
                    customerList
                )
            }
        })
    }

    fun sortingCustomerDate() {
        customerList.postValue(customerList.value?.let { repository.sortingCustomerDate(it) })
    }

    fun getClassificationName(): ArrayList<String> {
        var tempCustomerClassificationNameList: ArrayList<String> = arrayListOf()
        for (i in 0 until customerClassificationList.value?.size!!) {
            var tempName = customerClassificationList.value?.get(i)?.ClassificationName ?: "error"
            tempCustomerClassificationNameList.add(tempName)
        }
        return tempCustomerClassificationNameList
    }

    fun changeCurrentItem(customer: Customer) {
        currentItem.postValue(customerList.value?.get(customer.CustomerId.toInt()))
        currentClassificationItem.postValue(customerClassificationList.value?.get(customer.ClassificationId.toInt()))
    }

    fun getSingleClassificationName(clasisficationId: Long): String? {
        return customerClassificationList.value?.get(clasisficationId.toInt())?.ClassificationName
    }
}