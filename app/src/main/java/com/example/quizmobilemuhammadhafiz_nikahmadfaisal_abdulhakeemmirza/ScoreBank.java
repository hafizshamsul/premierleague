package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

public class ScoreBank {
    private String userEmail;
    private String userScore;
    private String datetime;
    private String point;

    public ScoreBank() {

    }

    public ScoreBank(String userEmail, String userScore, String datetime,String point) {
        this.userEmail = userEmail;
        this.userScore = userScore;
        this.datetime = datetime;
        this.point=point;

    }

    public String getPoint() { return point; }

    public void setPoint(String point) { this.point = point; }



    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }
}