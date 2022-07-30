package com.moveitech.dealerpay.dataModel.response.transaction

import com.moveitech.dealerpay.dataModel.general.Customer

data class TransactionResponse(
    val authCode: String,
    val canVoid: Boolean,
    val cardBrand: String,
    val charityRoundupAmount: Int,
    val customer: Customer,
    val department: String,
    val expDate: String,
    val isPaymentRequest: Boolean,
    val isVoided: Boolean,
    val last4: String,
    val message: String,
    val operation: String,
    val payShareAmount: Int,
    val referenceNumber: String,
    val saleAmount: Double,
    val success: Boolean,
    val totalAmount: Double,
    val transactionDate: String,
    val transactionId: String,
    val transactionType: String
)