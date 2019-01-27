package org.premiumapp.arforecast.internal

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setActionBarTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = title
}

fun Fragment.setActionBarSubTitle(subTitle: String) {
    (activity as AppCompatActivity).supportActionBar?.subtitle = subTitle
}