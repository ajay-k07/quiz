package com.ajai__lee.quiz.data;

import android.util.Log;

import com.ajai__lee.quiz.controller.AppController;
import com.ajai__lee.quiz.model.Question;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> questionArrayList = new ArrayList<>();

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0 ; i<response.length();i++){
                    try {
                        Question question = new Question();
                        question.setAnswer((String) response.getJSONArray(i).get(0));
                        question.setAnswertrue(response.getJSONArray(i).getBoolean(1));

                        // adding question to the List
                        questionArrayList.add(question);

                        //Log.d("hello", "onResponse: "+question);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(callBack!=null){
                        callBack.processFinished(questionArrayList);
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addTORequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
