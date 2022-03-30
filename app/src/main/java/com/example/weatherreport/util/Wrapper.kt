package com.example.weatherreport.util

class Wrapper<T>(var value: T) {
    fun changeValue(value: T) {
        this.value = value
    }
}