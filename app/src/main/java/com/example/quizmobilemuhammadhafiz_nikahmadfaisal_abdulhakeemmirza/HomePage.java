package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity implements View.OnClickListener{

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private TextView welcomepageText;
    private Button buttonLogout;
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        /*if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }*/

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        welcomepageText= findViewById(R.id.homeTextView);

        SharedPreferences sp=getApplicationContext().getSharedPreferences(LoginActivity.pref,0);

        welcomepageText.setText("Welcome, "+sp.getString("Username",null));
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        btn1 = findViewById(R.id.buttonOptionCard1);
        btn2 = findViewById(R.id.buttonOptionCard2);
        btn3 = findViewById(R.id.buttonOptionCard3);
        btn4 = findViewById(R.id.buttonOptionCard4);
        btn5 = findViewById(R.id.buttonOptionCard5);
        btn6 = findViewById(R.id.buttonOptionCard6);
        btn7 = findViewById(R.id.buttonOptionCard7);
        btn8 = findViewById(R.id.buttonOptionCard8);
        btn9 = findViewById(R.id.buttonOptionCard9);
        btn10 = findViewById(R.id.buttonOptionCard10);

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btn1){
            Toast.makeText(this,"Button 1",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","1");
            startActivity(intent);
        }else if(view==btn2){
            Toast.makeText(this,"Button 2",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","2");
            startActivity(intent);

        }else if(view==btn3){
            Toast.makeText(this,"Button 3",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","3");
            startActivity(intent);

        }else if(view==btn4){
            Toast.makeText(this,"Button 4",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","4");
            startActivity(intent);

        }else if(view==btn5){
            Toast.makeText(this,"Button 5",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","5");
            startActivity(intent);

        }else if(view==btn6){
            Toast.makeText(this,"Button 6",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","6");
            startActivity(intent);

        }else if(view==btn7){
            Toast.makeText(this,"Button 7",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","7");
            startActivity(intent);

        }else if(view==btn8){
            Toast.makeText(this,"Button 8",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","8");
            startActivity(intent);

        }else if(view==btn9){
            Toast.makeText(this,"Button 9",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","9");
            startActivity(intent);

        }else if(view==btn10){
            Toast.makeText(this,"Button 10",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(HomePage.this,QuizPage.class);
            intent.putExtra("option","10");
            startActivity(intent);

        }
        else if(view == buttonLogout){
            //logging out the user
            //firebaseAuth.signOut();
            SharedPreferences sp=getSharedPreferences(LoginActivity.pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this,LoginActivity.class));
            //startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
