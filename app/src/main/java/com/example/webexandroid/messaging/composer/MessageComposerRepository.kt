package com.example.webexandroid.messaging.composer

import android.util.Log
import com.ciscowebex.androidsdk.Webex
import com.ciscowebex.androidsdk.message.LocalFile
import com.ciscowebex.androidsdk.message.Mention
import com.ciscowebex.androidsdk.message.Message
import com.ciscowebex.androidsdk.CompletionHandler
import com.ciscowebex.androidsdk.utils.EmailAddress
import io.reactivex.Observable
import io.reactivex.Single


open class MessageComposerRepository(private val webex: Webex) {

    fun postToSpace(spaceId: String, message: String, plainText: Boolean, mentions: ArrayList<Mention>?, files: ArrayList<LocalFile>?): Observable<Message> {
        return Single.create<Message> { emitter ->
            val text: Message.Text? = if (plainText) {
                Message.Text.plain(message)
            } else {
                Message.Text.markdown(message, null, null)
            }
            webex.messages.postToSpace(spaceId, text, mentions, files, CompletionHandler { result ->
                if (result.isSuccessful) {
                    emitter.onSuccess(result.data!!)
                } else {
                    emitter.onError(Throwable(result.error?.errorMessage))
                }
            })
        }.toObservable()
    }

    fun postToPerson(email: EmailAddress, message: String, plainText: Boolean, files: ArrayList<LocalFile>?): Observable<Message> {
        return Single.create<Message> { emitter ->
            val text: Message.Text? = if (plainText) {
                Message.Text.plain(message)
            } else {
                Message.Text.markdown(message, null, null)
            }
            webex.messages.postToPerson("hachawla@cisco.com", text, null, CompletionHandler<Message> { result ->
                if (result.isSuccessful) {
                    Log.e("postperson","successful")
                    emitter.onSuccess(result.data!!)
                } else {
                    Log.e("postperson",result.error?.errorMessage)
                    emitter.onError(Throwable(result.error?.errorMessage))
                }
            })
        }.toObservable()
    }

    fun postToPerson(id: String, message: String, plainText: Boolean, files: ArrayList<LocalFile>?): Observable<Message> {
        return Single.create<Message> { emitter ->
            val text: Message.Text? = if (plainText) {
                Message.Text.plain(message)
            } else {
                Message.Text.markdown(message, null, null)
            }
            webex.messages.postToPerson("Y2lzY29zcGFyazovL3VzL1BFT1BMRS8wNzA4MGQ3My03Njc0LTRhMTEtYWFjMC02YWQzMGQ1NDNhNjE", text, null, CompletionHandler<Message> { result ->
                if (result.isSuccessful) {
                    Log.e("postperson","successful")
                    emitter.onSuccess(result.data!!)
                } else {
                    Log.e("postperson",result.error?.errorMessage)
                    emitter.onError(Throwable(result.error?.errorMessage))
                }
            })
        }.toObservable()
    }

    fun postMessageDraft(target: String, text: String): Observable<Message> {
        return Single.create<Message> { emitter ->

            val textdraft: Message.Draft =Message.draft(Message.Text.plain(text))
            webex.messages.post(target, textdraft, CompletionHandler { result ->
                if (result.isSuccessful) {
                    Log.e("postpersondirect","success")
                    emitter.onSuccess(result.data!!)
                } else {
                    Log.e("postpersondirect",result.error?.errorMessage)
                    emitter.onError(Throwable(result.error?.errorMessage))
                }
            })
        }.toObservable()
    }
}