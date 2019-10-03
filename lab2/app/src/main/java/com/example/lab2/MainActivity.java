package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMyClick1(View view) {
        // process button click
        setContentView(R.layout.first_layout);
    }
    public void onMyClick2(View view) {
        // process button click
        setContentView(R.layout.second_layout);
    }
    public void onMyClick3(View view) {
        // process button click
        setContentView(R.layout.activity_main);
    }

}
