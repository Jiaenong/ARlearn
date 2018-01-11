package com.example.user.arlearn;

/**
 * Created by user on 12/25/2017.
 */

public class QuizLibrary {

    private String quizID;
    private String question;
    private String choice1;
    private String choice2;
    private String choise3;
    private String answer;

    public QuizLibrary()
    {

    }

    public QuizLibrary(String question, String choice1, String choice2, String choise3, String answer)
    {
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choise3 = choise3;
        this.answer = answer;
    }
    public QuizLibrary(String quizID, String question, String choice1, String choice2, String choise3, String answer)
    {
        this.quizID = quizID;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choise3 = choise3;
        this.answer = answer;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoise3() {
        return choise3;
    }

    public void setChoise3(String choise3) {
        this.choise3 = choise3;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
