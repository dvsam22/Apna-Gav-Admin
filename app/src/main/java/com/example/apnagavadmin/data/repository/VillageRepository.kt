package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.LocalizedString
import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.util.AppError
import com.example.apnagavadmin.util.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class VillageRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val villageCollection = firestore.collection("villages")

    private fun DocumentSnapshot.toSafeLocalizedString(field: String): LocalizedString {
        val value = get(field)
        return when (value) {
            is Map<*, *> -> {
                LocalizedString(
                    en = value["en"] as? String ?: "",
                    hi = value["hi"] as? String ?: ""
                )
            }
            is String -> LocalizedString(en = value, hi = value)
            else -> LocalizedString()
        }
    }

    private fun DocumentSnapshot.toSafeVillage(): Village {
        val isAct = getBoolean("isActive") ?: getBoolean("active") ?: true
        return Village(
            id = id,
            villageName = toSafeLocalizedString("villageName"),
            sarpanchName = toSafeLocalizedString("sarpanchName"),
            sarpanchPhone = getString("sarpanchPhone") ?: "",
            district = toSafeLocalizedString("district"),
            state = toSafeLocalizedString("state"),
            pincode = getString("pincode") ?: "",
            lat = getDouble("lat") ?: getLong("lat")?.toDouble() ?: 0.0,
            lng = getDouble("lng") ?: getLong("lng")?.toDouble() ?: 0.0,
            image = getString("image") ?: "",
            isActive = isAct
        )
    }

    fun getVillages(): Flow<Resource<List<Village>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = villageCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Unknown error")))
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val villages = snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.toSafeVillage()
                    } catch (e: Exception) {
                        try {
                            doc.toObject(Village::class.java)?.copy(id = doc.id)
                        } catch (e2: Exception) {
                            null
                        }
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
            Resource.Error(AppError.FirestoreError(e.message ?: "Failed to add village"))
        }
    }

    suspend fun updateVillage(village: Village): Resource<Unit> {
        return try {
            if (village.id.isBlank()) return Resource.Error(AppError.FirestoreError("Invalid village ID"))
            villageCollection.document(village.id).set(village, SetOptions.merge()).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(AppError.FirestoreError(e.message ?: "Failed to update village"))
        }
    }

    suspend fun updateVillageStatus(villageId: String, isActive: Boolean): Resource<Unit> {
        return try {
            if (villageId.isBlank()) return Resource.Error(AppError.FirestoreError("Invalid village ID"))
            val updates = mapOf<String, Any>(
                "isActive" to isActive,
                "active" to isActive
            )
            villageCollection.document(villageId).update(updates).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            try {
                if (villageId.isBlank()) return Resource.Error(AppError.FirestoreError("Invalid village ID"))
                val updates = mapOf<String, Any>(
                    "isActive" to isActive,
                    "active" to isActive
                )
                villageCollection.document(villageId).set(updates, SetOptions.merge()).await()
                Resource.Success(Unit)
            } catch (e2: Exception) {
                Resource.Error(AppError.FirestoreError(e2.message ?: "Failed to update village status"))
            }
        }
    }

    suspend fun deleteVillage(villageId: String): Resource<Unit> {
        return try {
            villageCollection.document(villageId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(AppError.FirestoreError(e.message ?: "Failed to delete village"))
        }
    }
}
