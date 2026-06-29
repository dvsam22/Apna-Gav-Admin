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
        val subscription = firestore.collection("villages").document(villageId).collection("news")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener }
                val news = snapshot?.documents?.mapNotNull { it.toObject(News::class.java)?.copy(id = it.id) } ?: emptyList()
                trySend(Resource.Success(news))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveNews(villageId: String, news: News): Resource<Unit> = try {
        if (news.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("news").add(news).await()
        } else {
            firestore.collection("villages").document(villageId).collection("news").document(news.id).set(news).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteNews(villageId: String, newsId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("news").document(newsId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    fun getBanners(villageId: String): Flow<Resource<List<Banner>>> = callbackFlow {
        trySend(Resource.Loading())
        val subscription = firestore.collection("villages").document(villageId).collection("banners")
            .addSnapshotListener { snapshot, error ->
                if (error != null) { trySend(Resource.Error(error.message ?: "Error")); return@addSnapshotListener }
                val banners = snapshot?.documents?.mapNotNull { it.toObject(Banner::class.java)?.copy(id = it.id) } ?: emptyList()
                trySend(Resource.Success(banners))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveBanner(villageId: String, banner: Banner): Resource<Unit> = try {
        if (banner.id.isEmpty()) {
            firestore.collection("villages").document(villageId).collection("banners").add(banner).await()
        } else {
            firestore.collection("villages").document(villageId).collection("banners").document(banner.id).set(banner).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }

    suspend fun deleteBanner(villageId: String, bannerId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("banners").document(bannerId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(e.message ?: "Failed") }
}
