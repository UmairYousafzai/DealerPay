package com.moveitech.dealerpay.dataModel.request.cardPayment

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.moveitech.dealerpay.BR
import java.io.Serializable

 class CardPayment(
 
):Parcelable,BaseObservable()
 {
     val authOnly: Boolean = true
     var dealerId: String = ""
     var departmentId: String = ""
     val donationAmount: Int = 0
     val email: String = ""
     val hasDonation: Boolean = false
     var hasPayShare: Boolean = false
     var payShareAmount: Int = 0
     var paymentId: String = ""
     val phone: String = ""
     val reference: String = "xyz"
     val safe: Boolean = true
     var saleAmount: Int = 0
     val taxExempt: Boolean = true
     var token: String = ""
     var transactionTypeId: String = ""
     @get:Bindable
     var address1: String = "address1"
         set (value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.address1)

             }
         }

     @get:Bindable
     var address2: String = "address2"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.address2)

             }
         }

     @get:Bindable
     var city: String = "city"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.city)

             }
         }


     @get:Bindable
     var firstName: String = "firstname"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.firstName)

             }
         }

     @get:Bindable
     var lastName: String = "lastname"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.lastName)

             }
         }

     @get:Bindable
     var phoneNumber: String = "123 123 123"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.phoneNumber)

             }
         }

     @get:Bindable
     var state: String = "xyz"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.state)

             }
         }

     @get:Bindable
     var zipcode: String = "xyz"
         set(value) {
             if (field != value) {
                 field = value
                 notifyPropertyChanged(BR.zip)
             }
         }

     constructor(parcel: Parcel) : this() {
         dealerId = parcel.readString()!!
         departmentId = parcel.readString()!!
         hasPayShare = parcel.readByte() != 0.toByte()
         payShareAmount = parcel.readInt()
         paymentId = parcel.readString()!!
         saleAmount = parcel.readInt()
         token = parcel.readString()!!
         transactionTypeId = parcel.readString()!!
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
         parcel.writeString(dealerId)
         parcel.writeString(departmentId)
         parcel.writeByte(if (hasPayShare) 1 else 0)
         parcel.writeInt(payShareAmount)
         parcel.writeString(paymentId)
         parcel.writeInt(saleAmount)
         parcel.writeString(token)
         parcel.writeString(transactionTypeId)
     }

     override fun describeContents(): Int {
         return 0
     }

     companion object CREATOR : Parcelable.Creator<CardPayment> {
         override fun createFromParcel(parcel: Parcel): CardPayment {
             return CardPayment(parcel)
         }

         override fun newArray(size: Int): Array<CardPayment?> {
             return arrayOfNulls(size)
         }
     }
 }