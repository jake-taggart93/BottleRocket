package com.example.bottlerocket.viewmodel.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bottlerocket.repository.IRepository
import com.example.bottlerocket.viewmodel.CityViewModel
import kotlinx.coroutines.CoroutineScope

class CityViewModelProvider(
    private val repository: IRepository,
    private val coroutineScope: CoroutineScope
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityViewModel(repository, coroutineScope) as T
    }
}