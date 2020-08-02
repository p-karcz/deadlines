package com.example.deadlines.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)  val id: Int = 0,
    var name: String,
    @ColumnInfo(name = "end_time") var entTime: Long,
    var description: String
)

@Dao
interface TaskDao {
    @Insert
    fun insertTasks(vararg tasks: Task)

    @Delete
    fun deleteTasks(vararg tasks: Task)

    @Query("delete from Task")
    fun deleteAllTasks()

    @Update
    fun updateTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks(): LiveData<List<Task>>
}

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao
}

private lateinit var DATABASE_INSTANCE: TasksDatabase

fun getInstance(context: Context): TasksDatabase {
    synchronized(TasksDatabase::class.java) {
        if(!::DATABASE_INSTANCE.isInitialized) {
            DATABASE_INSTANCE = Room.databaseBuilder(
                context.applicationContext, TasksDatabase::class.java, "tasks").build()
        }
    }

    return DATABASE_INSTANCE
}
