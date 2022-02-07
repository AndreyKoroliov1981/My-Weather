package com.korol.myweather.repository

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep


@Keep class ListItem() : Parcelable {
    var id=0
    var city="empty"
    var typeRegion="empty"
    var region="empty"
    var latitude="empty"
    var longitude="empty"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        city = parcel.readString().toString()
        typeRegion = parcel.readString().toString()
        region = parcel.readString().toString()
        latitude = parcel.readString().toString()
        longitude = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(city)
        parcel.writeString(typeRegion)
        parcel.writeString(region)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListItem> {
        override fun createFromParcel(parcel: Parcel): ListItem {
            return ListItem(parcel)
        }

        override fun newArray(size: Int): Array<ListItem?> {
            return arrayOfNulls(size)
        }
    }
}