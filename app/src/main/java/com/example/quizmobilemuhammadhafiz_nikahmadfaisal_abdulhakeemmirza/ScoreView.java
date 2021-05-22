package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScoreView extends AppCompatActivity implements View.OnClickListener{
    private Button buttonRedirect_ProfileActivity;
    private Button buttonRedirect_CrudUser;
    private Button buttonRedirect_ScoreView;
    private Button buttonLogout;

    DatabaseReference reference;
    List<ScoreBank> scoreBankList;
    ListView listViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_view);

        reference = FirebaseDatabase.getInstance().getReference("score");
        scoreBankList = new ArrayList<>();
        listViewScore = (ListView) findViewById(R.id.listViewScore);

        buttonRedirect_ProfileActivity = (Button) findViewById(R.id.buttonRedirect_ProfileActivity);
        buttonRedirect_CrudUser = (Button) findViewById(R.id.buttonRedirect_CrudUser);
        buttonRedirect_ScoreView = (Button) findViewById(R.id.buttonRedirect_ScoreView);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonRedirect_ProfileActivity.setOnClickListener(this);
        buttonRedirect_CrudUser.setOnClickListener(this);
        buttonRedirect_ScoreView.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scoreBankList.clear();

                for(DataSnapshot scoreSnapshot:dataSnapshot.getChildren()){
                    ScoreBank sb=scoreSnapshot.getValue(ScoreBank.class);
                    scoreBankList.add(sb);
                }

                ScoreList adapter=new ScoreList(ScoreView.this,scoreBankList);
                listViewScore.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            finish();
            SharedPreferences sp=getSharedPreferences(LoginActivity.pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this,LoginActivity.class));
        }
        else if(view == buttonRedirect_ProfileActivity){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
        else if(view==buttonRedirect_CrudUser){
            finish();
            startActivity(new Intent(this, CrudUser.class));
        }
        else if(view == buttonRedirect_ScoreView){
            //
        }
    }
}
