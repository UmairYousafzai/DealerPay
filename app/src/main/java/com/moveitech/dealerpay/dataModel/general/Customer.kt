package com.moveitech.dealerpay.dataModel.general

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.moveitech.dealerpay.BR
import java.io.Serializable

 class Customer():Serializable, BaseObservable()
 {
     @get:Bindable
     var address1: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.address1)

             }
         }
     @get:Bindable
     var address2: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.address2)

             }
         }
     @get:Bindable
     var city: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.city)

             }
         }
     val companyName: String=""
     @get:Bindable
     var email: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.email)

             }
         }
     @get:Bindable
     var firstName: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.firstName)

             }
         }
     @get:Bindable
     var lastName: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.lastName)

             }
         }
     @get:Bindable
     var phoneNumber: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.phoneNumber)

             }
         }
     @get:Bindable
     var state: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.state)

             }
         }
     @get:Bindable
     var zipcode: String=""
         set(value) {
             if (field != value)
             {
                 field=value
                 notifyPropertyChanged(BR.zipcode)
             }
         } }