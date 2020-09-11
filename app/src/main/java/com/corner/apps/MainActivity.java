package com.corner.apps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); //Loading splash selama 2 detik
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent dashboard = new Intent(MainActivity.this, DashboardDrawwerActivity.class);
                    startActivity(dashboard);
                    finish();
                }
            }
        });
        tr.start(); // Menjalankan splash

    }
}
