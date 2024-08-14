package com.mohamedfayaskhan.studentmanager.actions

interface Actions<T> {
    fun create(data: T)
    fun update(data: T)
    fun read(): List<T>
    fun delete(id: String)
    fun refresh(studentId: String?)
    fun getUniqueId(): String
}