package com.mohamedfayaskhan.studentmanager.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Database {

        private var instance: FirebaseFirestore? = null
        fun getFirebaseFireStore(): FirebaseFirestore {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val fbInstance = Firebase.firestore
                instance = fbInstance
                return fbInstance
            }
        }
}