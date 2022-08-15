package com.moveitech.dealerpay.dataModel.utilModel

data class ApiErrorTwo(
    val status: Int,
    val title: String,
    val traceId: String,
    val type: String
)