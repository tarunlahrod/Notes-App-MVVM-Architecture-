package com.example.notesappmvvmarchitecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    // constructor

    // we pass application as parameter. Application is a subclass of context which we can use as a context to create NoteDatabase instance
    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    // Room doesn't allow the operations (e.g. insert, update, delete etc.) on the main thread as it would freeze the application
    // We instead need to implement them on a background thread.

    // The below 4 methods will work like APIs for our ViewModel, as it will only use these 4 methods to access the database

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    // LiveData operations are by default implemented on a background thread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    // for background thread implementation, we use Async Task
    // we define "static" so that it doesn't have a reference to the repository itself otherwise this could cause a memory leak
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        // since the class is static, we can't access the NoteDao of Repository directly. So have to pass it over a constructor.
        InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    // for background thread implementation, we use Async Task
    // we define "static" so that it doesn't have a reference to the repository itself otherwise this could cause a memory leak
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        // since the class is static, we can't access the NoteDao of Repository directly. So have to pass it over a constructor.
        UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    // for background thread implementation, we use Async Task
    // we define "static" so that it doesn't have a reference to the repository itself otherwise this could cause a memory leak
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        // since the class is static, we can't access the NoteDao of Repository directly. So have to pass it over a constructor.
        DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    // for background thread implementation, we use Async Task
    // we define "static" so that it doesn't have a reference to the repository itself otherwise this could cause a memory leak
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        // since the class is static, we can't access the NoteDao of Repository directly. So have to pass it over a constructor.
        DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
