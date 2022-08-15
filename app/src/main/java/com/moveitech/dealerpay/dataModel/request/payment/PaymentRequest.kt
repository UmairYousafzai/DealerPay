package com.moveitech.dealerpay.dataModel.request.payment

import com.moveitech.dealerpay.dataModel.general.Customer
import java.io.Serializable

data class PaymentRequest(

     var customer: Customer ?= null,
     var dealerId:  String="",
     var departmentId:  String="",
     val donationAmount:  Long=0,
     val hasDonation:  Boolean=false,
     var hasPayShare:  Boolean=false,
     var payShareAmount:  Long=0,
     var paymentId:  String="",
     val reference:  String="xyz",
     val safe:  Boolean=true,
     var saleAmount:  Long=0,
     var sendEmail:  Boolean=false,
     var sendSMS:  Boolean=false,
     val taxExempt:  Boolean=true,
     var transactionTypeId:  String="",
 ):Serializable