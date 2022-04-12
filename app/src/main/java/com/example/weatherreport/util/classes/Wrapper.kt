package com.example.weatherreport.util.classes

class Wrapper<T>(var value: T) {
    fun changeValue(value: T) {
        this.value = value
    }
}