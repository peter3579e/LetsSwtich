package com.peter.letsswtich.data.source

import com.peter.letsswtich.data.ChatRoom
import com.peter.letsswtich.data.Message
import com.peter.letsswtich.data.Result
import com.peter.letsswtich.data.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO



class DefaultLetsSwitchRepository(private val remoteDataSource: LetsSwitchDataSource,
                                  private val localDataSource: LetsSwitchDataSource) :
    LetsSwitchRepository {

        override suspend fun getUserItem(): List<User>{
            return remoteDataSource.getUserItem()
        }

        override suspend fun getChatItem(): List<ChatRoom>{
        return remoteDataSource.getChatItem()
        }

    override suspend fun getMessageItem(): List<Message>{
        return remoteDataSource.getMessageItem()
    }

}