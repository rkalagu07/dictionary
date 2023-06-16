package com.arasan.mytest.models

data class ResDictionaryItem(
    val meanings: List<Meaning>,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)