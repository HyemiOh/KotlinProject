package com.app.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.calculator.dao.HistoryDao
import com.app.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}