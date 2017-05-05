package com.example.rm.aa_tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);
    }

    public void onSplashPageClick(View view){
        Intent intent = new Intent(this, country.class);
        startActivity(intent);
        finish();
    }
//    COMMENTO A CASO
}
