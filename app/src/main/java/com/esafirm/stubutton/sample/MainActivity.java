package com.esafirm.stubutton.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.esafirm.stubutton.StuButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StuButton stuButton = ((StuButton) findViewById(R.id.stuButton));
        stuButton.setOnUnlockListener(new StuButton.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(getApplicationContext(), "Unlocked", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
