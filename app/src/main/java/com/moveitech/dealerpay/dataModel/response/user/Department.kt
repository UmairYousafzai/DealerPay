package com.moveitech.dealerpay.dataModel.response.user

import java.io.Serializable

data class Department(
    val defaultTransactionTypeId: String,
    val defaultTransactionTypeLabel: String,
    val departmentId: String,
    val departmentTypeId: String,
    val hasPayShare: Boolean,
    val name: String,
    val paySharePercentage: Double,
    val payShareThreshold: Any
): Serializable