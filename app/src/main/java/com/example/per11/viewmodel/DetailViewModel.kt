package com.example.per11.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.per11.modeldata.DataSiswa
import com.example.per11.repositori.RepositoryDataSiswa
import com.example.per11.uicontroller.DestinasiDetail
import kotlinx.coroutines.launch

sealed interface StatusUIDetail {
    data class Success(val satuSiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                StatusUIDetail.Success(repositoryDataSiswa.getSatuSiswa(idSiswa))
            } catch (e: Exception) {
                StatusUIDetail.Error
            }
        }
    }

    suspend fun hapusSatuSiswa() {
        val resp = repositoryDataSiswa.hapusSatuSiswa(idSiswa)
        if (resp.isSuccessful) println("Sukses hapus data: ${resp.message()}")
        else println("Gagal hapus data: ${resp.errorBody()}")
    }
}