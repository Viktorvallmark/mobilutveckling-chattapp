package com.example.firebasechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context: Context, private val messageList: ArrayList<Message>): RecyclerView.Adapter<ViewHolder>() {

    private val itemReceived = 1
    private val itemSent = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_recieved_layout, parent, false)
            ReceiveHolder(view)

        }else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_sent_layout, parent, false)
            SentHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder.javaClass == SentHolder::class.java){
            val currentMessage = messageList[position]
            val viewHolder = holder as SentHolder

            viewHolder.sentMessage.text = currentMessage.message
        }else {
            val currentMessage = messageList[position]
            val viewHolder = holder as ReceiveHolder
            viewHolder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return if(Firebase.auth.currentUser?.uid.equals(currentMessage.senderId)) {
            itemSent
        }else {
            itemReceived
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentHolder(item: View) : ViewHolder(item) {

        val sentMessage: TextView = item.findViewById(R.id.chatsent)
    }

    class ReceiveHolder(item: View) : ViewHolder(item) {

        val receiveMessage: TextView = item.findViewById(R.id.chatrecieved)
    }

}