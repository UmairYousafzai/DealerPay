package com.moveitech.dealerpay.dataModel.request.payment

import java.io.Serializable

data class SendPayment(
    val address1: String="",
    val address2: String="",
    val authOnly: String="",
    val city: String="",
    val dealerId: String="",
    val departmentId: String="",
    val donationAmount: String="",
    val email: String="",
    val firstName: String="",
    val hasDonation: String="",
    val hasPayShare: String="",
    val lastName: String="",
    val payShareAmount: String="",
    val paymentId: String="",
    val phone: String="",
    val reference: String="",
    val safe: String="",
    val saleAmount: String="",
    val state: String="",
    val taxExempt: String="",
    val token: String="",
    val transactionTypeId: String="",
    val zipcode: String=""
):Serializable