package com.example.shopgistic

import android.net.Uri
import java.io.Serializable

data class IsiListMainMenu(val title : String, val logo : Uri)
data class IsiListDetailProduk(
    val title : String,
    val logo : Uri,
    val weight : Float,
    val price : Float
    ) : Serializable
