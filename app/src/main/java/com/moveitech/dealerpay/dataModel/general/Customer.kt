package com.moveitech.dealerpay.dataModel.general

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.moveitech.dealerpay.BR
import java.io.Serializable

class Customer() : Serializable, BaseObservable() {
    @get:Bindable
    var address1: String = "address1"
        set(value) {
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
    val companyName: String = "company name"

    @get:Bindable
    var email: String = "email"
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.email)

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
    var zip: String = "xyz"
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.zip)
            }
        }
}