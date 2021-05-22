package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.HomePage;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.LoginActivity;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.QuestionBank;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.R;
import com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.ScoreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizPage extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private Button buttonBack;

    private long startTime;
    private long elapsedTime;
    int totalPoints = 0;

    String qtnId;
    List<String>key=new ArrayList<>();
    java.util.List<String> questionId=new ArrayList<>();
    //String[] questionId;
    Button b1,b2,b3,b4;
    TextView textviewQuestion;
    int correct=0;
    int total=0;
    int wrong=0;
    int limit=0;
    int op;
    DatabaseReference reference,ref;
    String[] List;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        b1=(Button)findViewById(R.id.buttonOption1);
        b2=(Button)findViewById(R.id.buttonOption2);
        b3=(Button)findViewById(R.id.buttonOption3);
        b4=(Button)findViewById(R.id.buttonOption4);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonBack = (Button) findViewById(R.id.buttonBack);



        textviewQuestion=(TextView)findViewById(R.id.textviewQuizQuestion);

        Intent intent=getIntent();
        option=intent.getStringExtra("option");

        op=Integer.parseInt(option);

        if(op==1){
            total=0;
            limit=total+5;
        }else if(op==2){
            total=2;
            limit=total+5;
        }else if(op==3){
            total=4;
            limit=total+5;
        }else if(op==4){
            total=6;
            limit=total+5;
        }else if(op==5){
            total=8;
            limit=total+5;
        }else if(op==6){
            total=10;
            limit=total+5;
        }else if(op==7){
            total=12;
            limit=total+5;
        }else if(op==8){
            total=7;
            limit=total+5;
        }else if(op==9){
            total=11;
            limit=total+5;
        }else if(op==10){
            total=14;
            limit=total+5;
        }
        getQuestionId();
    }
    int num=0;

    public void getQuestionId(){
        ref= FirebaseDatabase.getInstance().getReference().child("qtn");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                b1.setBackgroundTintList(null);
                b2.setBackgroundTintList(null);
                b3.setBackgroundTintList(null);
                b4.setBackgroundTintList(null);

                b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                b4.setBackgroundColor(Color.parseColor("#03A9F4"));

                questionId.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.i("children", "onDataChange: " + children);
                for (DataSnapshot child : children) {

                    Map<String, String> map = (Map) child.getValue();
                    Log.i("inside", "onDataChange: " + map);
                    questionId.add(map.get("questionId"));
                    textviewQuestion.setText(questionId.get(num));
                    //Toast.makeText(QuizPage.this, "" + questionId.get(num), Toast.LENGTH_SHORT).show();
                    num = num + 1;

                }
                updateQuestion(questionId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateQuestion(final List<String> questionId) {
        this.questionId=questionId;

        if(total==limit){
            Intent i=new Intent(QuizPage.this, ScoreActivity.class);
            i.putExtra("correct",""+correct);
            i.putExtra("totalPoints",""+totalPoints);
            startActivity(i);

        }else{
            reference= FirebaseDatabase.getInstance().getReference().child("qtn").child(questionId.get(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final QuestionBank question=dataSnapshot.getValue(QuestionBank.class);
                    textviewQuestion.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());

                    b1.setBackgroundTintList(null);
                    b2.setBackgroundTintList(null);
                    b3.setBackgroundTintList(null);
                    b4.setBackgroundTintList(null);

                    b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                    b4.setBackgroundColor(Color.parseColor("#03A9F4"));

                    b1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(b1.getText().toString().equals(question.getAnswer()))
                            {
                                total=total+1;

                                elapsedTime = elapsedTime + (System.currentTimeMillis() - startTime);
                                int earnedPoints = 0;

                                if(elapsedTime <2000){
                                    earnedPoints = 2000;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <4000){
                                    earnedPoints = 1800;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <6000){
                                    earnedPoints = 1600;
                                    totalPoints += earnedPoints;
                                }
                                else {
                                    earnedPoints = 1400;
                                    totalPoints += earnedPoints;
                                }

                                Toast.makeText(getApplicationContext(),"Correct! "+"+"+earnedPoints+"pts",Toast.LENGTH_SHORT).show();
                                b1.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //correct++;
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }else{
                                total=total+1;
                                Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong=wrong+1;
                                b1.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }else if(b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }
                        }
                    });

                    b2.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(b2.getText().toString().equals(question.getAnswer()))
                            {
                                total=total+1;

                                elapsedTime = elapsedTime + (System.currentTimeMillis() - startTime);
                                int earnedPoints = 0;

                                if(elapsedTime <2000){
                                    earnedPoints = 2000;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <4000){
                                    earnedPoints = 1800;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <6000){
                                    earnedPoints = 1600;
                                    totalPoints += earnedPoints;
                                }
                                else {
                                    earnedPoints = 1400;
                                    totalPoints += earnedPoints;
                                }

                                Toast.makeText(getApplicationContext(),"Correct! "+"+"+earnedPoints+"pts",Toast.LENGTH_SHORT).show();
                                b2.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //correct++;
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }else{
                                total=total+1;
                                Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong=wrong+1;
                                b2.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }else if(b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }
                        }
                    });

                    b3.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(b3.getText().toString().equals(question.getAnswer()))
                            {
                                total=total+1;

                                elapsedTime = elapsedTime + (System.currentTimeMillis() - startTime);
                                int earnedPoints = 0;

                                if(elapsedTime <2000){
                                    earnedPoints = 2000;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <4000){
                                    earnedPoints = 1800;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <6000){
                                    earnedPoints = 1600;
                                    totalPoints += earnedPoints;
                                }
                                else {
                                    earnedPoints = 1400;
                                    totalPoints += earnedPoints;
                                }

                                Toast.makeText(getApplicationContext(),"Correct! "+"+"+earnedPoints+"pts",Toast.LENGTH_SHORT).show();
                                b3.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //correct++;
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }else{
                                total=total+1;
                                Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong=wrong+1;
                                b3.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }else if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }
                        }
                    });

                    b4.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(b4.getText().toString().equals(question.getAnswer()))
                            {
                                total=total+1;

                                elapsedTime = elapsedTime + (System.currentTimeMillis() - startTime);
                                int earnedPoints = 0;

                                if(elapsedTime <2000){
                                    earnedPoints = 2000;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <4000){
                                    earnedPoints = 1800;
                                    totalPoints += earnedPoints;
                                }
                                else if(elapsedTime <6000){
                                    earnedPoints = 1600;
                                    totalPoints += earnedPoints;
                                }
                                else {
                                    earnedPoints = 1400;
                                    totalPoints += earnedPoints;
                                }

                                Toast.makeText(getApplicationContext(),"Correct! "+"+"+earnedPoints+"pts",Toast.LENGTH_SHORT).show();
                                b4.setBackgroundColor(Color.GREEN);
                                correct=correct+1;
                                Handler handler=new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);
                            }else{
                                total=total+1;
                                Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                                wrong=wrong+1;
                                b4.setBackgroundColor(Color.RED);
                                if(b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }else if(b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }else if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        b4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion(questionId);
                                    }
                                },1500);

                            }
                        }
                    });

                    buttonBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                    });

                    buttonLogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //logging out the user
                            firebaseAuth.signOut();
                            //closing activity
                            finish();
                            //starting login activity
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            //startActivity(new Intent(this, LoginActivity.class));
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
