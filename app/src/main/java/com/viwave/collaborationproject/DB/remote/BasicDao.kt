package com.viwave.collaborationproject.DB.remote

import androidx.room.*

@Dao
interface BasicDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Entity)

    @Update
    fun update(entity: Entity)

    fun getAll(): MutableList<Entity>

    @Delete
    fun delete(entity: Entity)

    @Delete
    fun deleteAll()
}