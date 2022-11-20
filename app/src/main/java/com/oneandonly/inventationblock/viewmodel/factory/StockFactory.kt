package com.oneandonly.inventationblock.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.viewmodel.StockViewModel

class StockFactory(private val repo : StockRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StockViewModel(repo) as T
    }
}