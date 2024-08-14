package com.mohamedfayaskhan.studentmanager.actions

interface DataFetcher<T> {
    fun getListData(list: List<T>)
    fun getSingleData(data: T)
}