package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.LoginActivity;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.R;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.ScoreBank;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    TextView welcomeTV;
    TextView statementTV;
    TextView scoreTV;
    TextView statement2TV;
    TextView pointsTV;
    Button homeButton;

    String score;
    String points;
    DatabaseReference databaseScore,user;
    final String shared="sharing";
    final String loginUs="loginUsername";
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences sp=getApplicationContext().getSharedPreferences(LoginActivity.pref,0);
        welcomeTV=(TextView)findViewById(R.id.markStatementTextView);
        statementTV=(TextView)findViewById(R.id.markTextView);
        scoreTV=(TextView)findViewById(R.id.markScoreTextView);
        statement2TV=(TextView)findViewById(R.id.markTextView);
        pointsTV=(TextView)findViewById(R.id.totalPointsTextView);
        homeButton=(Button)findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);

        welcomeTV.setText("Hi, "+sp.getString("Username",null));
        Intent intent=getIntent();
        score=intent.getStringExtra("correct");
        points=intent.getStringExtra("totalPoints");

        databaseScore = FirebaseDatabase.getInstance().getReference("score");
        //user=FirebaseDatabase.getInstance().getReference("user");

        //firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();

        String user=sp.getString("Username",null);
        String scorer=score;
        String point=points;
        Date datetime= Calendar.getInstance().getTime();

        ScoreBank sb=new ScoreBank(user,scorer,datetime.toString(), point);

        String scoreId = databaseScore.push().getKey();

        databaseScore.child(scoreId).setValue(sb);

        scoreTV.setText(score+"/5");

        pointsTV.setText(points+"pts");
    }

    @Override
    public void onClick(View v){
        if(v==homeButton){
            startActivity(new Intent(this,HomePage.class));
        }
    }


}