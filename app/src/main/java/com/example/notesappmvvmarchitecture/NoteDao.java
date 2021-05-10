package com.example.notesappmvvmarchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    // we do not have an annotation for "Delete All" query in Room. So we have to write our own code for that purpose
    // we use a Query annotation to define a delete query method
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    // we need one more method to get all the notes for our recycler view
    // we again use the Query annotation
    // we used LiveData<> here, so that anytime the database is updated, we get notified and hence the updates can be visible ASAP
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

}
