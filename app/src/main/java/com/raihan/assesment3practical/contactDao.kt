package com.raihan.assesment3practical

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface contactDao {

    @Query("SELECT * FROM contact_table")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM contact_table WHERE phone_number LIKE :roll LIMIT 1")
     fun findByRoll(roll: Int): Contact

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Contact)

    @Delete
    suspend fun delete(student: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("SELECT (SELECT COUNT(*) FROM contact_table) == 0")
    fun isEmpty(): Boolean

}