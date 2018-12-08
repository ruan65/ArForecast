package org.premiumapp.arforecast.data.provider

import org.premiumapp.arforecast.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}