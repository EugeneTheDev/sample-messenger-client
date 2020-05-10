package com.eugene.sample_message_client.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessengerRepository : CoroutineScope {
    override val coroutineContext = Dispatchers.IO

    private val messengerApi = Retrofit.Builder()
        .baseUrl(MessengerApi.HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MessengerApi::class.java)


    fun register(username: String, firstName: String, lastName: String) = async {
        val user = User(username, firstName, lastName)
        messengerApi.register(user)
            .execute()
            .body()
    }

    fun getAllUsers() = async {
        messengerApi.getAllUsers().execute().body()
    }

    fun getAllMessages() = async {
        messengerApi.getAllMessages().execute().body()
    }

    fun postMessage(author: String, text: String) = async {
        val messageRequest = MessageRequest(author, text)
        messengerApi.postMessage(messageRequest).execute().body()
    }

    fun getLastMessages(sinceId: Int) = async {
        messengerApi.getLastMessages(sinceId).execute().body()
    }
}