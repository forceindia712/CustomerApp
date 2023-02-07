package com.example.assecoapp.model.item

data class Customer(
    var CustomerId: Long = 0,
    var ClassificationId: Long = 0,
    var CustomerName: String = "",
    var CustomerNIP: String = "",
    var CustomerCity: String = "",
    var DateTime: String = "",
    var isDeleted: Boolean = false
)

