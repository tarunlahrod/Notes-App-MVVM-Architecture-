package com.example.notesappmvvmarchitecture;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    // we wanted to make this class "singleton" means we cannot have multiple instances of this class
    // we just want one single instance of this class which is the 'instance' variable (static)
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {

        // we only want to instantiate this database if we already don't have an instance
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
