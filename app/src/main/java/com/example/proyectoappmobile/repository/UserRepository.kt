package com.example.proyectoappmobile.repository

import com.example.proyectoappmobile.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    suspend fun addUser(user: User): Result<String> {
        return try {
            val newUserRef = usersCollection.document(user.id)
            newUserRef.set(user).await()
            Result.success("User added successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getUserById(userId: String): Result<User?> {
        return try {
            val documentSnapshot = usersCollection.document(userId).get().await()
            val user = documentSnapshot.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getAllUsers(): Result<List<User>> {
        return try {
            val querySnapshot = usersCollection.get().await()
            val users = querySnapshot.toObjects(User::class.java)
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updateUser(user: User): Result<String> {
        return try {
            usersCollection.document(user.id).set(user).await()
            Result.success("User updated successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun deleteUser(userId: String): Result<String> {
        return try {
            usersCollection.document(userId).delete().await()
            Result.success("User deleted successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
