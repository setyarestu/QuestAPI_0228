package com.example.per11.uicontroller

import com.example.per11.R

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit_siswa"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}