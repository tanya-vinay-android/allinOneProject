package com.example.website;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SearchHomeBar extends AppCompatActivity {
    AutoCompleteTextView editText;
    List<String> tags;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.search);
        editText = findViewById(R.id.auto_complete_tv);


    }
}
