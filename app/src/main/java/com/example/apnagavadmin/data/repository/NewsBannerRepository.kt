package com.example.apnagavadmin.data.repository

import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.util.AppError
import com.example.apnagavadmin.util.Resource
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class NewsBannerRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    companion object {
        var serviceAccountProvider: (() -> InputStream)? = null
    }
    
    private val client = OkHttpClient()
    private val projectId = "apna-gaon-f57f6"
    private var cachedToken: String? = null
    private var tokenExpiry: Long = 0

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

    private fun DocumentSnapshot.toNews(): News? {
        return try {
            News(
                id = id,
                title = toSafeLocalizedString("title"),
                description = toSafeLocalizedString("description"),
                image = getString("image") ?: "",
                date = getLong("date") ?: System.currentTimeMillis(),
                villageId = getString("villageId") ?: "",
                category = getString("category") ?: "news"
            )
        } catch (e: Exception) { null }
    }

    private fun DocumentSnapshot.toBanner(): Banner? {
        return try {
            Banner(
                id = id,
                imageUrl = getString("imageUrl") ?: "",
                title = toSafeLocalizedString("title"),
                discountText = getString("discountText") ?: "",
                link = getString("link") ?: "",
                villageId = getString("villageId") ?: ""
            )
        } catch (e: Exception) { null }
    }

    private fun DocumentSnapshot.toAppNotification(): AppNotification? {
        return try {
            AppNotification(
                id = id,
                title = toSafeLocalizedString("title"),
                message = toSafeLocalizedString("message"),
                date = getLong("date") ?: System.currentTimeMillis(),
                villageId = getString("villageId") ?: ""
            )
        } catch (e: Exception) { null }
    }

    private suspend fun getAccessToken(): String? = withContext(Dispatchers.IO) {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiry) {
            return@withContext cachedToken
        }

        try {
            val stream = serviceAccountProvider?.invoke()
            if (stream == null) {
                android.util.Log.e("FCM", "serviceAccountProvider returned null stream")
                return@withContext null
            }
            
            val credentials = GoogleCredentials.fromStream(stream)
                .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
            
            val token = credentials.refreshAccessToken()
            cachedToken = token.tokenValue
            tokenExpiry = token.expirationTime.time - 60000 // 1 minute buffer
            android.util.Log.d("FCM", "New access token generated successfully")
            cachedToken
        } catch (e: Exception) {
            android.util.Log.e("FCM", "Error getting access token: ${e.message}", e)
            null
        }
    }

    private suspend fun sendFcmNotification(villageId: String, title: String, message: String) {
        // Use NonCancellable to ensure notification is sent even if the caller's scope is cancelled
        withContext(Dispatchers.IO + NonCancellable) {
            // Fix Topic Name
            val topic = if (villageId.startsWith("village_")) villageId else "village_$villageId"
            
            android.util.Log.d("FCM", "--- START NOTIFICATION PROCESS ---")
            android.util.Log.d("FCM", "Topic: $topic")
            
            try {
                android.util.Log.d("FCM", "Step 1: Requesting Access Token...")
                val token = getAccessToken()
                if (token == null) {
                    android.util.Log.e("FCM", "❌ ABORT: Access Token is NULL")
                    return@withContext
                }
                android.util.Log.d("FCM", "Step 2: Access Token obtained.")

                android.util.Log.d("FCM", "Step 3: Preparing JSON payload...")
                val safeTitle = title.replace("\"", "\\\"")
                val safeMessage = message.replace("\"", "\\\"")

                val json = """
                    {
                      "message": {
                        "topic": "$topic",
                        "notification": {
                          "title": "$safeTitle",
                          "body": "$safeMessage"
                        },
                        "android": {
                          "priority": "high",
                          "notification": {
                            "sound": "default"
                          }
                        }
                      }
                    }
                """.trimIndent()

                val body = json.toRequestBody("application/json".toMediaType())
                val request = Request.Builder()
                    .url("https://fcm.googleapis.com/v1/projects/$projectId/messages:send")
                    .post(body)
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                android.util.Log.d("FCM", "Step 4: Executing Network Call to FCM...")
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    if (response.isSuccessful) {
                        android.util.Log.d("FCM", "✅ SUCCESS! Notification sent successfully")
                        android.util.Log.d("FCM", "Server Response: $responseBody")
                    } else {
                        android.util.Log.e("FCM", "❌ SERVER ERROR: ${response.code}")
                        android.util.Log.e("FCM", "Error Response: $responseBody")
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("FCM", "🔥 CRITICAL ERROR: ${e.message}", e)
            } finally {
                android.util.Log.d("FCM", "--- END NOTIFICATION PROCESS ---")
            }
        }
    }
    
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
                    trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error"))); return@addSnapshotListener 
                }
                val news = snapshot?.documents?.mapNotNull { it.toNews() } 
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
            // Send notification for new news
            sendFcmNotification(vId, "Nayi Khabar: ${news.title.hi}", news.description.hi)
        } else {
            firestore.collection("villages").document(vId).collection("news").document(news.id).set(news.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteNews(actualVillageId: String, newsId: String): Resource<Unit> = try {
        firestore.collection("villages").document(actualVillageId).collection("news").document(newsId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

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
                trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error"))); return@addSnapshotListener 
            }
            val banners = snapshot?.documents?.mapNotNull { it.toBanner() } ?: emptyList()
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
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteBanner(villageId: String, bannerId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("banners").document(bannerId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

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
                    trySend(Resource.Error(AppError.FirestoreError(error.message ?: "Error"))); return@addSnapshotListener 
                }
                val notifications = snapshot?.documents?.mapNotNull { it.toAppNotification() }
                    ?.sortedByDescending { it.date } ?: emptyList()
                trySend(Resource.Success(notifications))
            }
        awaitClose { subscription.remove() }
    }

    suspend fun saveNotification(actualVillageId: String, notification: AppNotification): Resource<Unit> = try {
        val vId = if (actualVillageId == "all") notification.villageId else actualVillageId
        if (vId.isEmpty() || vId == "all") throw Exception("Invalid Village ID")

        if (notification.id.isEmpty()) {
            firestore.collection("villages").document(vId).collection("notifications").add(notification.copy(villageId = vId)).await()
            // Send notification
            sendFcmNotification(vId, notification.title.hi, notification.message.hi)
        } else {
            firestore.collection("villages").document(vId).collection("notifications").document(notification.id).set(notification.copy(villageId = vId)).await()
        }
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }

    suspend fun deleteNotification(villageId: String, notificationId: String): Resource<Unit> = try {
        firestore.collection("villages").document(villageId).collection("notifications").document(notificationId).delete().await()
        Resource.Success(Unit)
    } catch (e: Exception) { Resource.Error(AppError.FirestoreError(e.message ?: "Failed")) }
}
