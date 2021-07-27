package com.decagonhq.clads.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.decagonhq.clads.data.domain.client.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    /*Add Client to Database*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addClient(client: Client)

    /*Get Single Client in the Database*/
    @Query("SELECT * FROM client_details_table WHERE id = :clientId")
    suspend fun readClient(clientId: Int): Client

    /*Get All Client in the Database*/
    @Transaction
    @Query("SELECT * FROM client_details_table")
    fun readAllClients(): Flow<List<Client>>

    /*Delete client in the Database*/
    @Query("DELETE FROM client_details_table WHERE id = :clientId")
    suspend fun deleteClient(clientId: Int)

    /*Delete All clients in the Database*/
    @Query("DELETE FROM client_details_table")
    suspend fun deleteAllClients()

    /*Update Client*/
    @Update
    suspend fun updateClientDetails(client: Client)
}
