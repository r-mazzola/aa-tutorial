package com.example.rm.aa_tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class country extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
    }

    public void goToCountry(String country){
        Intent intent = new Intent(this, questions.class);
        intent.putExtra("country", country);
        startActivity(intent);
        finish();
    }

    public void onEuropeClick(View v){
        goToCountry("Europe");
    }

    public void onAmericaClick(View v){
        goToCountry("America");
    }

    public void onAsiaClick(View v){
        goToCountry("Asia");
    }
}
