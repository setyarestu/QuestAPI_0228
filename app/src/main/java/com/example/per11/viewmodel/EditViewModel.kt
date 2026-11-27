package com.example.per11.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.per11.repositori.RepositoryDataSiswa
import com.example.per11.uicontroller.DestinasiEdit
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiEdit.itemIdArg])

    init {
        viewModelScope.launch {
            try {
                uiStateSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                    .toUiStateSiswa(isEntryValid = true)
            } catch (e: Exception) {
                println("Gagal memuat data: ${e.message}")
            }
        }
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean =
        with(uiState) { nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank() }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    suspend fun editSatuSiswa() {
        if (validasiInput()) {
            val resp = repositoryDataSiswa.editSatuSiswa(idSiswa, uiStateSiswa.detailSiswa.toDataSiswa())
            if (resp.isSuccessful) println("Sukses edit data: ${resp.message()}")
            else println("Gagal edit data: ${resp.errorBody()}")
        }
    }
}