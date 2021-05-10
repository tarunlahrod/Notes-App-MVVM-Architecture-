package com.example.notesappmvvmarchitecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // we declare an object of our View Model and in onCreate we assign this variable
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // in onCreate method, we assign the noteViewModel object, but we won't cal "new NoteViewModel" because then
        // we will just create an instance with every new activity. Instead we ask the Android system for ViewModel because
        // the system knows when it has to create a new ViewModel instance and when it has to provide an already existing instance.
        // Note: the code shown in the video in not working with the current Androidx (maybe some version issues) so the updated code
        // is below
        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update RecyclerView
                Toast.makeText(MainActivity.this, "On Changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}