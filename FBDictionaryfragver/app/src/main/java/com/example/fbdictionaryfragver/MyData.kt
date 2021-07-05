package com.example.fbdictionaryfragver

data class MyData(var word:String, var meaning:String, var isExpanded:Boolean = false) {
    constructor():this("sample","info", false)
}