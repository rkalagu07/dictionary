package com.arasan.mytest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arasan.mytest.models.ResDictionary
import com.arasan.mytest.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val mRepository: DictionaryRepository) : ViewModel() {


    private val _response = MutableLiveData<String>()
    val responseDictionaryData: LiveData<String>
        get() = _response

     fun getAllDictionaryData(search: String) = viewModelScope.launch {
        mRepository.getAllData(search).let {response ->

            if (response.isSuccessful){
                _response.postValue(response.body()!!.string())
            }else{
                _response.postValue(""+response.code())
            }
        }
    }
}