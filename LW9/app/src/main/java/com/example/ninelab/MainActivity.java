package com.example.ninelab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new AddFragment()).commit();
    }
    boolean selection = false;

    public void ChangeFrag(View view)
    {
        if (selection){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new AddFragment()).commit();
            selection = false;
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ListFragment()).commit();
            selection = true;
        }
    }
}