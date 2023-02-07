package com.example.assecoapp.model.repository

import com.example.assecoapp.model.item.Customer
import com.example.assecoapp.model.item.CustomerClasisfication
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomerRepository {

    val TAG = "CustomerRepository"

     fun dateToString(): String {
        val actualDateTime = LocalDateTime.now()
        val myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formattedDateTime = actualDateTime.format(myFormatObj)
        return formattedDateTime
    }

    fun sortingClassificationName(customerClassificationList: ArrayList<CustomerClasisfication>, customerList: ArrayList<Customer>): ArrayList<Customer> {
        var tempCustomerClassificationList = customerClassificationList.sortedWith(compareBy { it.ClassificationName })
        var tempCustomerList: ArrayList<Customer> = arrayListOf()
        for (i in 0 until tempCustomerClassificationList.size) {
            for (j in 0 until customerList.size)
            {
                if(tempCustomerClassificationList.get(i).ClassificationId == customerList.get(j).ClassificationId)
                    tempCustomerList.add(customerList.get(j))
            }
        }
        return tempCustomerList
    }

    fun sortingCustomerName(customerList: ArrayList<Customer>): ArrayList<Customer> {
        val tempCustomerList = customerList.sortedWith(compareBy { it.CustomerName })
        return ArrayList(tempCustomerList)
    }

    fun sortingCustomerDate(customerList: ArrayList<Customer>): ArrayList<Customer> {
        val tempCustomerList = customerList.sortedWith(compareBy { it.DateTime })
        return ArrayList(tempCustomerList)
    }
}