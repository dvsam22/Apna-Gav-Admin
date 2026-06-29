package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class LabourRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getCategories(): Flow<Resource<List<LabourCategory>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = firestore.collection("labour_categories").addSnapshotListener { snapshot, error ->
            if (error != null) { trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener }
            val categories = snapshot?.documents?.mapNotNull { it.toObject(LabourCategory::class.java)?.copy(id = it.id) } ?: emptyList()
            trySend(Resource.Success(categories))
        }
        awaitClose { subscription.remove() }
    }

    fun getProviders(villageId: String, categoryId: String): Flow<Resource<List<LabourProvider>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = firestore.collection("villages").document(villageId)
            .collection("labour").whereEqualTo("categoryId", categoryId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Error"))
                    return@addSnapshotListener
                }
                val providers = snapshot?.documents?.mapNotNull { doc ->
                    try {
                        doc.toObject(LabourProvider::class.java)?.copy(id = doc.id)
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()
                trySend(Resource.Success(providers))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveProvider(villageId: String, provider: LabourProvider): Resource<Unit> = try {
        if (provider.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("labour").add(provider).await()
        } else {
            firestore.collection("villages").document(villageId).collection("labour").document(provider.id).set(provider).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteProvider(villageId: String, providerId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("labour").document(providerId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}

class ConstructionRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<ConstructionHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = firestore.collection("villages").document(villageId).collection("construction")
        val finalQuery = if (categoryId != null) baseQuery.whereEqualTo("categoryId", categoryId) else baseQuery

        val subscription = finalQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Error(error.message ?: "Error"))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(ConstructionHub::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            trySend(Resource.Success(hubs))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(villageId: String, hub: ConstructionHub): Resource<Unit> = try {
        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("construction").add(hub).await()
        } else {
            firestore.collection("villages").document(villageId).collection("construction").document(hub.id).set(hub).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteHub(villageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("construction").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}

class TransportRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<TransportHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = firestore.collection("villages").document(villageId).collection("transport")
        val finalQuery = if (categoryId != null) baseQuery.whereEqualTo("categoryId", categoryId) else baseQuery

        val subscription = finalQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Error(error.message ?: "Error"))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(TransportHub::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            trySend(Resource.Success(hubs))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(villageId: String, hub: TransportHub): Resource<Unit> = try {
        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("transport").add(hub).await()
        } else {
            firestore.collection("villages").document(villageId).collection("transport").document(hub.id).set(hub).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteHub(villageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("transport").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}

class MandiRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getPrices(villageId: String): Flow<Resource<List<MandiPrice>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = firestore.collection("villages").document(villageId).collection("mandi")
            .addSnapshotListener { snapshot, error ->
                if (error != null) { trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener }
                val prices = snapshot?.documents?.mapNotNull { it.toObject(MandiPrice::class.java)?.copy(id = it.id) } ?: emptyList()
                trySend(Resource.Success(prices))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun savePrice(villageId: String, price: MandiPrice): Resource<Unit> = try {
        if (price.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("mandi").add(price).await()
        } else {
            firestore.collection("villages").document(villageId).collection("mandi").document(price.id).set(price).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deletePrice(villageId: String, priceId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("mandi").document(priceId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}

class HealthRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String): Flow<Resource<List<HealthHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = firestore.collection("villages").document(villageId).collection("health")
            .addSnapshotListener { snapshot, error ->
                if (error != null) { trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener }
                val hubs = snapshot?.documents?.mapNotNull { it.toObject(HealthHub::class.java)?.copy(id = it.id) } ?: emptyList()
                trySend(Resource.Success(hubs))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(villageId: String, hub: HealthHub): Resource<Unit> = try {
        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("health").add(hub).await()
        } else {
            firestore.collection("villages").document(villageId).collection("health").document(hub.id).set(hub).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteHub(villageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("health").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}
