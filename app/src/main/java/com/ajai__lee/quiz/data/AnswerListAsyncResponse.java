package com.ajai__lee.quiz.data;

import com.ajai__lee.quiz.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
     void processFinished(ArrayList<Question> questionArrayList);
}
