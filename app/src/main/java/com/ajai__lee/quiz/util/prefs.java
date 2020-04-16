package com.ajai__lee.quiz.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class prefs {
    private SharedPreferences preferences;

    public prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void savehighscore(int score) {
        int currentscore = score;
        int lastscore = preferences.getInt("high_score", 0);

        if (currentscore > lastscore) {
            preferences.edit().putInt("high_score", currentscore).apply();
        }

    }

    public int gethighscore() {
        return preferences.getInt("high_score", 0);

    }
    public void setstate(int index){
        preferences.edit().putInt("index_value",index).apply();
    }
    public int getstate(){
        return preferences.getInt("index_value",0);
    }
}