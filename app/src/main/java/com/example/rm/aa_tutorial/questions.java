package com.example.rm.aa_tutorial;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class questions extends AppCompatActivity {

    private boolean done;
    private int questionNo;
    private int score;
    private int totalQuestion;
    private int errorNo;
    private int skipNo;
    private String choosenCountry;
    private String[] questions;
    private String[] answers;
    private String[] hints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //definisco i set di domande/risposte/indizi in base al continente scelto
        Intent i=getIntent();
        choosenCountry=i.getStringExtra("country");
        switch (choosenCountry){
            case "Europe":
                questions = getResources().getStringArray(R.array.questionsEurope);
                answers = getResources().getStringArray(R.array.answersEurope);
                hints = getResources().getStringArray(R.array.hintsEurope);
                break;
            case "America":
                questions = getResources().getStringArray(R.array.questionsAmerica);
                answers = getResources().getStringArray(R.array.answersAmerica);
                hints = getResources().getStringArray(R.array.hintsAmerica);
                break;
            case "Asia":
                questions = getResources().getStringArray(R.array.questionsEurope);
                answers = getResources().getStringArray(R.array.answersEurope);
                hints = getResources().getStringArray(R.array.hintsEurope);
                break;
            case "Africa":
                questions = getResources().getStringArray(R.array.questionsEurope);
                answers = getResources().getStringArray(R.array.answersEurope);
                hints = getResources().getStringArray(R.array.hintsEurope);
                break;
            default:
                questions = getResources().getStringArray(R.array.questionsEurope);
                answers = getResources().getStringArray(R.array.answersEurope);
                hints = getResources().getStringArray(R.array.hintsEurope);
                break;
        }

        //Domanda
        TextView t = (TextView)findViewById(R.id.question);
        t.setText(questions[questionNo].toUpperCase());
        //Score
        TextView tScore = (TextView)findViewById(R.id.score);
        tScore.setText("SCORE: "+score);
        //Numero domande
        totalQuestion=questions.length;
        TextView tNumQ = (TextView)findViewById(R.id.numberQuestion);
        tNumQ.setText((questionNo+1)+"/"+totalQuestion);
        //Nascondo la parte relativa alla risposta
        findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
        findViewById(R.id.correctOrNot).setVisibility(View.INVISIBLE);
        findViewById(R.id.nextButton).setVisibility(View.INVISIBLE);
    }
    //Gestione animazioni, visibility ecc dopo aver dato la risposta
    public void answerSubmitted(){
        findViewById(R.id.tickcross).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0,0,2000,0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(R.id.buttonLayout).setVisibility(View.INVISIBLE);
                findViewById(R.id.hintButton).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.correctOrNot).setVisibility(View.VISIBLE);
                findViewById(R.id.nextButton).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.tickcross).startAnimation(animation);
    }
    //Vai a schermata dei risultati
    public void goToResult(View v){
        Intent intent = new Intent(this, result.class);
        intent.putExtra("score", score);
        intent.putExtra("skip", skipNo);
        startActivity(intent);
        finish();
    }
    //Gestione errori
    public void errorMngmt(){
        errorNo++;
        TextView errTV = (TextView)findViewById(R.id.errors);
        errTV.setTextColor(Color.parseColor("#F7AC7A"));
        errTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        String err = "";
        int cont=errorNo;
        while(cont>0){
            err+="X";
            cont--;
        }
        errTV.setText(err);

        if(errorNo==1){
            getCustomToast("PRIMO ERRORE! Ti restano ancora 2 possibilità", Toast.LENGTH_LONG);
        }
    }
    //Custom Toast Notification
    public void getCustomToast(String msg, int duration){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.customToast));

        TextView txtMsg = (TextView) layout.findViewById(R.id.toastText);
        txtMsg.setText(msg);

        Toast custToast = new Toast(getApplicationContext());
        custToast.setGravity(Gravity.TOP, 0, 170);
        custToast.setDuration(duration);
        custToast.setView(layout);
        custToast.show();
    }

    @TargetApi(21)
    public void onAswerClick(View v){
        View view = this.getCurrentFocus();
        if (view != null) {
            //Nascondi tastiera virtuale
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(done == false){
            String answer = ((EditText)findViewById(R.id.answer)).getText().toString();
            String correctAnswer = answers[questionNo];
            correctAnswer=correctAnswer.toUpperCase();
            answer=answer.toUpperCase();
            if(answer.equals(correctAnswer)) {
                TextView t = (TextView)findViewById(R.id.correctOrNot);
                t.setText("ESATTO!");
                //Suono
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.corretto);
                mp.start();
                //Settaggio img risposta corretta
                ImageView i = (ImageView)findViewById(R.id.tickcross);
                i.setImageDrawable(getDrawable(R.drawable.weirdtick));
                //Gestione animazione risposta
                answerSubmitted();
                //Aggiorno il punteggio
                score+=10;
                TextView tScore = (TextView)findViewById(R.id.score);
                tScore.setText("SCORE: "+score);
            }else {
                TextView t = (TextView)findViewById(R.id.correctOrNot);
                t.setText("SBAGLIATO!!!");
                //Suono
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sbagliato);
                mp.start();
                //Settaggio img risposta sbagliata
                ImageView i = (ImageView)findViewById(R.id.tickcross);
                i.setImageDrawable(getDrawable(R.drawable.weirdcross));
                //Gestione TextView Errori
                errorMngmt();
                //Gestione animazione risposta
                answerSubmitted();
            }
            done=true;
        }
    }

    public void onSkipClick(View v){
        if(questionNo<(questions.length-1)) {
            if (done == false) {
                skipNo++;
                if (questionNo < (questions.length - 1)) {
                    //Domanda successiva
                    questionNo++;
                    TextView t = (TextView) findViewById(R.id.question);
                    t.setText(questions[questionNo].toUpperCase());
                    //Aggiorno numero domanda
                    TextView tNumQ = (TextView) findViewById(R.id.numberQuestion);
                    tNumQ.setText((questionNo + 1) + "/" + totalQuestion);

                    EditText et = (EditText) findViewById(R.id.answer);
                    et.setText("");
                    //Mostra tasitera virtuale quando editText ha il focus
                    et.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }else {
            goToResult(v);
        }
    }

    public void onHintClick(View v){
        //Toast notification
        getCustomToast(hints[questionNo], Toast.LENGTH_SHORT);
    }

    public void onNextClick(View v){
        if(errorNo==3) {
            goToResult(v);
            return;
        }
        if(done){
            if(questionNo<(questions.length-1))
            {
                //Domanda successiva
                questionNo++;
                TextView t = (TextView) findViewById(R.id.question);
                t.setText(questions[questionNo].toUpperCase());
                //Aggiorno numero domanda
                TextView tNumQ = (TextView)findViewById(R.id.numberQuestion);
                tNumQ.setText((questionNo+1)+"/"+totalQuestion);
                //Visualizzo bottoni domanda
                findViewById(R.id.buttonLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.hintButton).setVisibility(View.VISIBLE);
                //Nascondo elementi risposta
                findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
                findViewById(R.id.correctOrNot).setVisibility(View.INVISIBLE);
                findViewById(R.id.nextButton).setVisibility(View.INVISIBLE);
                EditText et = (EditText) findViewById(R.id.answer);
                et.setText("");
                //Mostra tasitera virtuale quando editText ha il focus
                et.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                //Tocca SCORE per terminare il gioco
                if(questionNo==15){
                    //Toast notification
                    getCustomToast("Tocca SCORE per terminare", Toast.LENGTH_LONG);
                }

                done=false;
            }else {
                goToResult(v);
            }
        }

    }
}
