package com.esafirm.stubutton.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esafirm.stubutton.StuButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StuButton stuButton = findViewById(R.id.stuButton);
        stuButton.setOnUnlockListener(new StuButton.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(getApplicationContext(), "Unlocked", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
