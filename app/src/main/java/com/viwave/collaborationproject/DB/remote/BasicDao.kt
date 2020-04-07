/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/27/20 3:56 PM
 */

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

    fun search(caseNumber:String): Entity
}