package com.example.webexandroid.messaging.spaces.detail

import com.ciscowebex.androidsdk.message.Message

interface MessageReceiveListener {
    fun onMessageReceived(message: Message)
}