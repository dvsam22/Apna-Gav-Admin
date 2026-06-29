package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class VillageRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val villageCollection = firestore.collection("villages")

    fun getVillages(): Flow<Resource<List<Village>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = villageCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Error(error.message ?: "Unknown error"))
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val villages = snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.toObject(Village::class.java)?.copy(id = doc.id)
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(Resource.Success(villages))
            }
        }
        awaitClose { subscription.remove() }
    }

    suspend fun addVillage(village: Village): Resource<Unit> {
        return try {
            villageCollection.add(village).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to add village")
        }
    }

    suspend fun updateVillage(village: Village): Resource<Unit> {
        return try {
            villageCollection.document(village.id).set(village).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update village")
        }
    }

    suspend fun deleteVillage(villageId: String): Resource<Unit> {
        return try {
            villageCollection.document(villageId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to delete village")
        }
    }
}
