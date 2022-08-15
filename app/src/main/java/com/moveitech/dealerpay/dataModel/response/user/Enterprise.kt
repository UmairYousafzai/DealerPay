package com.moveitech.dealerpay.dataModel.response.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Enterprise(
    val id_local:Int,
    val dealers: List<Dealer>,
    val enterpriseId: String,
    val name: String
): Serializable