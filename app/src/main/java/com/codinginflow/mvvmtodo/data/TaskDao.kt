package com.codinginflow.mvvmtodo.data

import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hidecompleted: Boolean): Flow<List<Task>> =
        when(sortOrder){
            SortOrder.BY_DATE-> getTasksSortedByDateCreated(query,hidecompleted)
            SortOrder.BY_NAME-> getTasksSortedByName(query,hidecompleted)
        }

    @Query("SELECT * FROM task_table WHERE(completed != :hidecompleted or completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hidecompleted:Boolean): Flow<List<Task>>


    @Query("SELECT * FROM task_table WHERE(completed != :hidecompleted or completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hidecompleted:Boolean): Flow<List<Task>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun  deleteCompletedTasks()


}