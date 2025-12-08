package com.example.intentparcelable

import android.os.Parcel
import android.os.Parcelable

data class Mahasiswa(
    val name: String?,
    val email: String?,
    val nim: String?,
    val umur: Int?,
    val kota: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(nim)
        parcel.writeValue(umur)
        parcel.writeString(kota)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Mahasiswa> {
        override fun createFromParcel(parcel: Parcel): Mahasiswa = Mahasiswa(parcel)
        override fun newArray(size: Int): Array<Mahasiswa?> = arrayOfNulls(size)
    }
}
