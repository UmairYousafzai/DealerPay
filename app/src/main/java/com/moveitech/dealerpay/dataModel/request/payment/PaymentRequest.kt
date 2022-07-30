package com.moveitech.dealerpay.dataModel.request.payment

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moveitech.dealerpay.dataModel.general.Customer
import java.io.Serializable

 data class PaymentRequest(
     val address1:  String="",
     val address2:  String="",
     val authOnly:  String="",
     val city:  String="",
     var customer: Customer ?= null,
     val dealerId:  String="",
     val departmentId:  String="",
     val donationAmount:  String="",
     val email:  String="",
     val firstName:  String="",

     val hasDonation:  String="",
     val hasPayShare:  String="",
     val lastName:  String="",
     val payShareAmount:  String="",
     val paymentId:  String="",
     val phone:  String="",
     val reference:  String="",
     val safe:  String="",
     val saleAmount:  String="",
     val sendEmail:  String="",
     val sendSMS:  String="",
     val state:  String="",
     val taxExempt:  String="",
     val token:  String="",
     val transactionTypeId:  String="",
     val zipcode:  String=""
 ):Serializable