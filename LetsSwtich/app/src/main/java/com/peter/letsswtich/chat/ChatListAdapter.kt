package com.peter.letsswtich.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.letsswtich.NavigationDirections
import com.peter.letsswtich.data.ChatRoom
import com.peter.letsswtich.databinding.FragmentProfileBinding
import com.peter.letsswtich.databinding.ItemChatListBinding

class ChatListAdapter(val viewModel: ChatViewModel) : ListAdapter<ChatRoom, ChatListAdapter.ChatListViewHolder> (DiffCallback){
    class ChatListViewHolder(private var binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root),LifecycleOwner{
        fun bind(chatRoom: ChatRoom, viewModel: ChatViewModel){

            binding.chatRoom = chatRoom
            binding.viewModel = viewModel
            binding.lifecycleOwner = this


            // Chat room has been filtered, the attendee info only holds the other user's info
            val friendInfo = chatRoom.attendeesInfo.first()
            Log.d("ChatListAdapter","Adapter has run")



            binding.textChatName.text = friendInfo.userName
            binding.image = friendInfo.userImage


            binding.root.setOnClickListener{
                view: View ->
                view.findNavController().navigate(NavigationDirections.navigateToChatroomFragment(friendInfo.userEmail,friendInfo.userName))
                Log.d("ChatListAdapter","value of userinfo = ${friendInfo.userEmail}")
                Log.d("ChatListAdapter","value of userinfo = ${friendInfo.userName}")
            }

//            Log.d("ChatListAdapter","value of userinfo = ${friendInfo.userEmail}")
//            Log.d("ChatListAdapter","value of userinfo = ${friendInfo.userName}")

            binding.executePendingBindings()

        }

        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun onAttach() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun onDetach() {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(getItem(position) as ChatRoom,viewModel)


        viewModel.latestTimeMessage.value = getItem(position).latestMessageTime

        Log.d("ChatListAdapter","value of getItem = ${viewModel.latestTimeMessage.value}")


//        holder.itemView.setOnClickListener{
//            Navigation.createNavigateOnClickListener(NavigationDirections.navigateToChatroomFragment())
//            Log.d("ChatListAdapter","value of getItem = ${getItem(position)}")
//        }
    }



    companion object DiffCallback : DiffUtil.ItemCallback<ChatRoom>() {
        override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom): Boolean {
            return oldItem.chatRoomId == newItem.chatRoomId
        }
    }
}