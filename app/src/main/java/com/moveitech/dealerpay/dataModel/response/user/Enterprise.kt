package com.moveitech.dealerpay.dataModel.response.user

import java.io.Serializable

data class Enterprise(
    val dealers: List<Dealer>,
    val enterpriseId: String,
    val name: String
): Serializable