package com.pkarcz.deadlines.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var description: String,
    var time: Long,
    var inProgress: Boolean = true
) {
    data class TaskBuilder(
        var name: String? = null,
        var description: String? = null,
        var time: Long = 0
    ) {
        fun name(name: String) = apply { this@TaskBuilder.name = name }
        fun description(description: String) = apply { this@TaskBuilder.description = description }
        fun time(time: Long) = apply { this@TaskBuilder.time += time }
        fun reset() {
            this.name = null
            this.description = null
            this.time = 0
        }
        fun build() = Task(0, name!!, description!!, time)
    }
}

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task): Long

    @Delete
    fun deleteTask(task: Task)

    @Query("delete from Task")
    fun deleteAllTasks()

    @Query("select * from Task where id = :taskId")
    fun getTask(taskId: Int): LiveData<Task>

    @Query("update Task set inProgress = 0 where id = :taskId")
    fun changeProgressToFalse(taskId: Int)

    @Query("select * from Task where inProgress = 1")
    fun getTasksInProgress(): LiveData<List<Task>>

    @Query("select * from Task where inProgress = 0")
    fun getUnfinishedTasks(): LiveData<List<Task>>
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

