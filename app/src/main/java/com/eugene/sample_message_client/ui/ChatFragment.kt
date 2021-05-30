package com.eugene.sample_message_client.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eugene.sample_message_client.R
import com.eugene.sample_message_client.data.MessengerRepository
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import kotlin.concurrent.fixedRateTimer

class ChatFragment : Fragment(), CoroutineScope {
    override val coroutineContext = Dispatchers.Main

    companion object {
        const val USERNAME = "username"
    }

    private lateinit var username: String
    private val repository = MessengerRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username = arguments?.getString(USERNAME) ?: throw IllegalArgumentException("No username passed")
        val adapter = MessagesAdapter(username)
        messagesList.adapter = adapter


        fixedRateTimer(daemon = true, initialDelay = 0, period = 1000) {
            launch {
                val lastMessages = repository.getLastMessages(adapter.lastMessageId).await()

                if (lastMessages?.isNotEmpty() == true) {
                    adapter.addMessages(lastMessages)
                    messagesList.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

        send.setOnClickListener {
            val messageText = inputMessage.text.toString()

            if (messageText.isNotBlank()) {
                launch {
                    inputMessage.text.clear()
                }
            }
        }

    }


}