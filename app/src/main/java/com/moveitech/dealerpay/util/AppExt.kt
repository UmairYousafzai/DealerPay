package com.moveitech.dealerpay.util


import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String) {
    val snackbar = Snackbar
        .make(this.requireView(), message, Snackbar.LENGTH_LONG)
    snackbar.show()
}

fun Activity.showSnackBar(message: String) {

    val snackbar = Snackbar
        .make(
            findViewById<View>(android.R.id.content).rootView, message, Snackbar.LENGTH_LONG
        )
    snackbar.show()
}

fun Fragment.showAlertDialog(msg: String) {
    var newMessage = msg
    if (newMessage.isEmpty()) {
        newMessage = "Unable to process your request \nPlease try again later !!"
    }
//    AlertMessageDialog.newInstance(newMessage)
//        .show(requireActivity().supportFragmentManager, AlertMessageDialog.TAG)
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun getDate(): String {
    val calendar = Calendar.getInstance()

    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    return format.format(calendar.time)
}

fun Context.getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
    var strAdd = ""
    val geocoder = Geocoder(this, Locale.getDefault())
    try {
        val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
        if (addresses != null) {
            val returnedAddress: Address = addresses[0]
            val strReturnedAddress = StringBuilder("")
            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
            }
            strAdd = strReturnedAddress.toString()
            Log.e("MyCurrentloctionaddress", strReturnedAddress.toString())
        } else {
            Log.e("MyCurrentloctionaddress", "No Address returned!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("MyCurrentloctionaddress", "Canont get Address!")
    }
    return strAdd
}


fun Fragment.hideToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.showToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

fun NavController.safeNavigate(direction: NavDirections) {

    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun View.preventTwoClick() {
    this.isEnabled = false
    this.postDelayed({ this.isEnabled = true }, 500)
}

fun Fragment.closeKeyBoard() {
    val view = requireActivity().currentFocus
    if (view != null) {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun stringToFormatDate(date: String?): String {
    var date1: Date? = null
    var formatDate = ""
    try {

        date1 = date?.let {
            val array = it.split("+")
            SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss").parse(array[0])
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val dateFormat: DateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm aaa")
    if (date1 != null) {
        formatDate = dateFormat.format(date1)
    }
    return formatDate
}

fun SearchView.observer(callBack: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {

            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { callBack.invoke(it) }
            return false
        }
    })
}

fun AutoCompleteTextView.setupAdapter(list: ArrayList<String>) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, list)
    setAdapter(adapter)
}