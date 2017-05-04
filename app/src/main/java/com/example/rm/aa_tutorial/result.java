package com.example.rm.aa_tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private int result;
    private int skippate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i=getIntent();
        result=i.getIntExtra("score", 0);
        skippate=i.getIntExtra("skip", 0);

        findViewById(R.id.resultLogo).setVisibility(View.INVISIBLE);
        TextView tResult  = (TextView)findViewById(R.id.scoreResult);
        tResult.setVisibility(View.INVISIBLE);
        tResult.setSingleLine(false);
        tResult.setText("PUNTEGGIO FINALE: "+result+" \nDOMANDE SALTATE: "+skippate);
    }

    public void onWindowFocusChanged (boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        findViewById(R.id.resultLogo).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0,0,-1000,0);
        animation.setDuration(700);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.scoreResult).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.resultLogo).startAnimation(animation);
    }

    public void onRestart(View v){
        Intent intent = new Intent(this, country.class);
        startActivity(intent);
        finish();
    }


}
