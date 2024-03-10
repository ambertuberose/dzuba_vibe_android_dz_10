package com.example.catfacts.model

import android.os.Parcel
import android.os.Parcelable

data class CatFact(
    val fact: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatFact> {
        override fun createFromParcel(parcel: Parcel): CatFact {
            return CatFact(parcel)
        }

        override fun newArray(size: Int): Array<CatFact?> {
            return arrayOfNulls(size)
        }
    }
}