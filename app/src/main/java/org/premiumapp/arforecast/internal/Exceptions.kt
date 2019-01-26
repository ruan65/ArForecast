package org.premiumapp.arforecast.internal

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.IOException

class ExceptionNoConnectivity: IOException()
class LocationPermissionNotGrantedException() : Exception()

fun Fragment.setActionBarTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = title
}