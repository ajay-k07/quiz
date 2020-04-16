package com.ajai__lee.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.ajai__lee.quiz.data.AnswerListAsyncResponse;
import com.ajai__lee.quiz.data.QuestionBank;
import com.ajai__lee.quiz.model.Question;
import com.ajai__lee.quiz.model.Score;
import com.ajai__lee.quiz.util.prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private TextView questiontextview;
   private TextView currentscore;
   private TextView higestscore;
   private TextView counttextview;
   private Button truebutton;
   private Button falsebutton;
   private ImageButton nextbutton;
   private ImageButton backbutton;
   private int scorecounter = 0;
   public int currentQuestionIndex;
   private Score score;
   private prefs prefs;
   private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score();
        prefs = new prefs(MainActivity.this);

        Log.d("second", "onCreate: "+prefs.gethighscore());








        // gettint the button by id
        nextbutton = findViewById(R.id.next_button);
        backbutton = findViewById(R.id.back_button);
        questiontextview = findViewById(R.id.question_view);
        counttextview = findViewById(R.id.count_view);
        currentscore = findViewById(R.id.current_scoretextview);
        higestscore = findViewById(R.id.hignest_scoretextview);
        truebutton = findViewById(R.id.true_button);
        falsebutton = findViewById(R.id.false_button);

        //setting onClick Listner
        nextbutton.setOnClickListener(this);
        backbutton.setOnClickListener(this);
        truebutton.setOnClickListener(this);
        falsebutton.setOnClickListener(this);


        currentscore.setText(MessageFormat.format("Current score : {0}", String.valueOf(score.getScore())));
        higestscore.setText(MessageFormat.format("high score : {0}", String.valueOf(prefs.gethighscore())));


        Log.d("sec", "onCreate: "+prefs.getstate());
    //    currentQuestionIndex = prefs.getstate();



        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questiontextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                counttextview.setText(currentQuestionIndex+"/"+questionArrayList.size());
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                if (currentQuestionIndex>0){
                    currentQuestionIndex = currentQuestionIndex - 1 % questionList.size();
                    updatequestion();
            }
                break;
            case R.id.next_button:
                currentQuestionIndex = currentQuestionIndex + 1 % questionList.size();
                updatequestion();
                break;
            case R.id.true_button:
                checkanswer(true);
                updatequestion();
                break;
            case R.id.false_button:
                checkanswer(false);
                updatequestion();
                break;
        }
    }

    private void updatequestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questiontextview.setText(question);
       counttextview.setText(currentQuestionIndex+"/"+questionList.size()        );


    }

    private void checkanswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswertrue();
        int toastMessageId = 0;
        if(userChooseCorrect == answerIsTrue){
            fadeview();
            addpoints();
            toastMessageId = R.string.Correct_answer;


        }
        else {
            shakeAnimation();
            subpoints();
            toastMessageId = R.string.Wrong_answer;

        }
        Toast.makeText(MainActivity.this,toastMessageId,Toast.LENGTH_SHORT).show();

    }
    private void addpoints(){
        scorecounter +=100;
        score.setScore(scorecounter);
        currentscore.setText(MessageFormat.format("Current score : {0}", String.valueOf(score.getScore())));
    }
    private void subpoints(){
        scorecounter -=100;
        if(scorecounter>0){
            score.setScore(scorecounter);
        }
        else{
            scorecounter = 0;
            score.setScore(scorecounter);
        }
        currentscore.setText(MessageFormat.format("Current score : {0}", String.valueOf(score.getScore())));
    }
    private void fadeview(){
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(1);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                currentQuestionIndex = currentQuestionIndex + 1 % questionList.size();
                updatequestion();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                currentQuestionIndex = currentQuestionIndex + 1 % questionList.size();
                updatequestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        prefs.savehighscore(score.getScore());
        prefs.setstate(currentQuestionIndex);
        super.onPause();

    }
}
