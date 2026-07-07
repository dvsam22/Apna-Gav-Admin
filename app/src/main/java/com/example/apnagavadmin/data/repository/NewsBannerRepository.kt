package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.Banner
import com.example.apnagavadmin.data.model.News
import com.example.apnagavadmin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NewsBannerRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    
    fun getNews(villageId: String): Flow<Resource<List<News>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("news")
        } else {
            firestore.collection("villages").document(villageId).collection("news")
        }
        val subscription = baseQuery
            .addSnapshotListener { snapshot, error ->
                if (error != null) { 
                    android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                    trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener 
                }
                val news = snapshot?.documents?.mapNotNull { it.toObject(News::class.java)?.copy(id = it.id) } 
                    ?.sortedByDescending { it.date } ?: emptyList()
                trySend(Resource.Success(news))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveNews(actualVillageId: String, news: News): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") news.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (news.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("news").add(news.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("news").document(news.id).set(news.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteNews(actualVillageId: String, newsId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("news").document(newsId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    fun getBanners(villageId: String): Flow<Resource<List<Banner>>> = callbackFlow {
        trySend(Resource.Loading())
        val query = if (villageId == "all") {
            firestore.collectionGroup("banners")
        } else {
            firestore.collection("villages").document(villageId).collection("banners")
        }
        val subscription = query.addSnapshotListener { snapshot, error ->
            if (error != null) { 
                android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener 
            }
            val banners = snapshot?.documents?.mapNotNull { it.toObject(Banner::class.java)?.copy(id = it.id) } ?: emptyList()
            trySend(Resource.Success(banners))
        }
        awaitClose { subscription.remove() }
    }

    suspend fun saveBanner(actualVillageId: String, banner: Banner): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") banner.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (banner.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("banners").add(banner.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("banners").document(banner.id).set(banner.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteBanner(villageId: String, bannerId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("banners").document(bannerId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    fun getNotifications(villageId: String): Flow<Resource<List<com.example.apnagavadmin.data.model.AppNotification>>> = callbackFlow {
        trySend(Resource.Loading())
        val baseQuery = if (villageId == "all") {
            firestore.collectionGroup("notifications")
        } else {
            firestore.collection("villages").document(villageId).collection("notifications")
        }
        val subscription = baseQuery
            .addSnapshotListener { snapshot, error ->
                if (error != null) { 
                    android.util.Log.e("FirestoreError", "Index required: ${error.message}")
                    trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener 
                }
                val notifications = snapshot?.documents?.mapNotNull { it.toObject(com.example.apnagavadmin.data.model.AppNotification::class.java)?.copy(id = it.id) }
                    ?.sortedByDescending { it.date } ?: emptyList()
                trySend(Resource.Success(notifications))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveNotification(actualVillageId: String, notification: com.example.apnagavadmin.data.model.AppNotification): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") notification.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (notification.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("notifications").add(notification.copy(villageId = vId)).await()
        } else {
            firestore.collection("villages").document(vId).collection("notifications").document(notification.id).set(notification.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteNotification(villageId: String, notificationId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("notifications").document(notificationId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}
