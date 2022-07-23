package com.moveitech.dealerpay.dataModel.response.transaction

data class Customer(
    val address1: String,
    val address2: String,
    val city: String,
    val companyName: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val state: String,
    val zip: String
)