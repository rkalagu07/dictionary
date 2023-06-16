package com.arasan.mytest.repository

import com.arasan.mytest.api.ApiService
import javax.inject.Inject

class DictionaryRepository
@Inject constructor(private val apiService: ApiService) {
    suspend fun getAllData(search: String) = apiService.getAllData(search);
}