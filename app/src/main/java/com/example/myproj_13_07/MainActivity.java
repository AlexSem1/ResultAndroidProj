package com.example.myproj_13_07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                startActivity(new Intent(this,BasicActivity.class));
                break;
            default:
                break;
        }
    }
}
