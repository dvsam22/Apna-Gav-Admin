package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.util.AppError
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
            if (error != null) { trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error"))); return@addSnapshotListener }
            val categories = snapshot?.documents?.mapNotNull { it.toObject(LabourCategory::class.java)?.copy(id = it.id) } ?: emptyList()
            trySend(Resource.Success(categories))
        }
        awaitClose { subscription.remove() }
    }

    fun getProviders(villageId: String, categoryId: String): Flow<Resource<List<LabourProvider>>> = callbackFlow {
        trySend(Resource.Loading())
        val query = if (villageId == "all") {
            firestore.collectionGroup("labour")
        } else {
            firestore.collection("villages").document(villageId).collection("labour").whereEqualTo("categoryId", categoryId)
        }
        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val providers = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(LabourProvider::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            val filtered = if (villageId == "all") {
                providers.filter { it.categoryId == categoryId }
            } else {
                providers
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveProvider(actualVillageId: String, provider: LabourProvider): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") provider.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")
        
        if (provider.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("labour").add(provider.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("labour").document(provider.id).set(provider.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteProvider(actualVillageId: String, providerId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("labour").document(providerId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}

class ConstructionRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<ConstructionHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("construction")
        } else {
            val q = firestore.collection("villages").document(villageId).collection("construction")
            if (categoryId != null) q.whereEqualTo("categoryId", categoryId) else q
        }

        val subscription = baseQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(ConstructionHub::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            val filtered = if (villageId == "all" && categoryId != null) {
                hubs.filter { it.categoryId == categoryId }
            } else {
                hubs
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(actualVillageId: String, hub: ConstructionHub): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") hub.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("construction").add(hub.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("construction").document(hub.id).set(hub.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteHub(actualVillageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("construction").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}

class TransportRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<TransportHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("transport")
        } else {
            val q = firestore.collection("villages").document(villageId).collection("transport")
            if (categoryId != null) q.whereEqualTo("categoryId", categoryId) else q
        }

        val subscription = baseQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(TransportHub::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            val filtered = if (villageId == "all" && categoryId != null) {
                hubs.filter { it.categoryId == categoryId }
            } else {
                hubs
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(actualVillageId: String, hub: TransportHub): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") hub.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("transport").add(hub.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("transport").document(hub.id).set(hub.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteHub(actualVillageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("transport").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}

class MandiRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getPrices(villageId: String, categoryId: String? = null): Flow<Resource<List<MandiPrice>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("mandi")
        } else {
            val q = firestore.collection("villages").document(villageId).collection("mandi")
            if (categoryId != null) q.whereEqualTo("categoryId", categoryId) else q
        }
        
        val subscription = baseQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val prices = snapshot?.documents?.mapNotNull { it.toObject(MandiPrice::class.java)?.copy(id = it.id) } ?: emptyList()
            
            val filtered = if (villageId == "all" && categoryId != null) {
                prices.filter { it.categoryId == categoryId }
            } else {
                prices
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun savePrice(actualVillageId: String, price: MandiPrice): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") price.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (price.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("mandi").add(price.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("mandi").document(price.id).set(price.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deletePrice(actualVillageId: String, priceId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("mandi").document(priceId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}

class HealthRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<HealthHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("health")
        } else {
            val q = firestore.collection("villages").document(villageId).collection("health")
            if (categoryId != null) q.whereEqualTo("categoryId", categoryId) else q
        }

        val subscription = baseQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { it.toObject(HealthHub::class.java)?.copy(id = it.id) } ?: emptyList()
            
            val filtered = if (villageId == "all" && categoryId != null) {
                hubs.filter { it.categoryId == categoryId }
            } else {
                hubs
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(actualVillageId: String, hub: HealthHub): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") hub.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("health").add(hub.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("health").document(hub.id).set(hub.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteHub(actualVillageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("health").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}

class FamilyFunctionRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun getHubs(villageId: String, categoryId: String? = null): Flow<Resource<List<FamilyFunctionHub>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("family_functions")
        } else {
            val q = firestore.collection("villages").document(villageId).collection("family_functions")
            if (categoryId != null) q.whereEqualTo("categoryId", categoryId) else q
        }

        val subscription = baseQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error")))
                return@addSnapshotListener
            }
            val hubs = snapshot?.documents?.mapNotNull { doc ->
                try {
                    doc.toObject(FamilyFunctionHub::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            } ?: emptyList()
            
            val filtered = if (villageId == "all" && categoryId != null) {
                hubs.filter { it.categoryId == categoryId }
            } else {
                hubs
            }
            trySend(Resource.Success(filtered))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveHub(actualVillageId: String, hub: FamilyFunctionHub): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") hub.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (hub.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("family_functions").add(hub.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("family_functions").document(hub.id).set(hub.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteHub(actualVillageId: String, hubId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("family_functions").document(hubId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}
