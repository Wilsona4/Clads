package com.decagonhq.clads.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    /*Add Clients to Database*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClients(clients: List<ClientEntity>)


    /*Get User in the Database*/
    @Transaction
    @Query("SELECT * FROM client_details_table")
    fun readClients(): Flow<List<ClientEntity>>


    @Query("SELECT * FROM client_details_table WHERE id LIKE:clientId")
    fun readClient(clientId: Int): Flow<List<ClientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addClient(client: ClientEntity): Long


    /*Delete userProfile in the Database*/
    @Query("DELETE FROM client_details_table")
    suspend fun deleteClients()


    /*Delete userProfile in the Database*/
    @Delete
    suspend fun deleteClient(client: ClientEntity): Int
}

