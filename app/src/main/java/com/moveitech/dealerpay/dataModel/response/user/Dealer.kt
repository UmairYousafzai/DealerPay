package com.moveitech.dealerpay.dataModel.response.user

import java.io.Serializable

data class Dealer(
    val charityName: Any,
    val charityRoundupEnabled: Boolean,
    val dealerId: String,
    val departments: ArrayList<Department>,
    val enterpriseId: String,
    val logoUrl: Any,
    val name: String
): Serializable