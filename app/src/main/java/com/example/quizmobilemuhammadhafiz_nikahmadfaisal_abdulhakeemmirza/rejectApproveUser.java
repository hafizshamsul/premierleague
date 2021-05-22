package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rejectApproveUser extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewEmail;
    private Button buttonLogout;
    private Button buttonRedirect_ProfileActivity;
    private Button buttonRedirect_CrudUser;
    private Button buttonRedirect_ScoreView;
    private Button buttonRedirect_Registered;
    private Button buttonRedirect_Pending;

    List<UserBank> userTempBankList;
    DatabaseReference databaseTempUsers,databaseUsers;
    ListView listViewTempUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_approve_user);
        databaseTempUsers = FirebaseDatabase.getInstance().getReference("tempUser");

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonRedirect_ProfileActivity = (Button) findViewById(R.id.buttonRedirect_ProfileActivity);
        buttonRedirect_CrudUser = (Button) findViewById(R.id.buttonRedirect_CrudUser);
        buttonRedirect_ScoreView = (Button) findViewById(R.id.buttonRedirect_ScoreView);
        buttonRedirect_Registered = (Button) findViewById(R.id.buttonRedirect_Registered);
        buttonRedirect_Pending = (Button) findViewById(R.id.buttonRedirect_Pending);

        buttonLogout.setOnClickListener(this);
        buttonRedirect_ProfileActivity.setOnClickListener(this);
        buttonRedirect_CrudUser.setOnClickListener(this);
        buttonRedirect_ScoreView.setOnClickListener(this);
        buttonRedirect_Registered.setOnClickListener(this);
        buttonRedirect_Pending.setOnClickListener(this);

        userTempBankList = new ArrayList<>();
        listViewTempUser = (ListView) findViewById(R.id.listViewTempUser);

        listViewTempUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserBank userBank = userTempBankList.get(i);
                updateUser(userBank.getAddress(), userBank.getAdmin(), userBank.getAge(), userBank.getPasswordhash(), userBank.getUserId(), userBank.getUsername());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTempUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userTempBankList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    UserBank userBank = userSnapshot.getValue(UserBank.class);
                    userTempBankList.add(userBank);
                }
                UserTempList adapter = new UserTempList(rejectApproveUser.this, userTempBankList);
                listViewTempUser.setAdapter(adapter);
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
            startActivity(new Intent(this,LoginActivity.class));
        }
        else if(view == buttonRedirect_ProfileActivity){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
        else if(view == buttonRedirect_CrudUser){
            //
        }
        else if(view == buttonRedirect_ScoreView){
            finish();
            startActivity(new Intent(this, ScoreView.class));
        }
        else if(view == buttonRedirect_Registered){
            finish();
            startActivity(new Intent(this, CrudUser.class));
        }
        else if(view == buttonRedirect_Pending){
            //
        }
    }

    private void updateUser(final String address,final String admin,final int age, final String passwordhash, final String userId, final String username){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.acceptreject, null);
        dialogBuilder.setView(dialogView);

        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewCrudName);
        final EditText editTextAddress = (EditText) dialogView.findViewById(R.id.editTextCrudAddress);
        final EditText editTextAdmin = (EditText) dialogView.findViewById(R.id.editTextCrudAdmin);
        final EditText editTextAge = (EditText) dialogView.findViewById(R.id.editTextCrudAge);
        final EditText editTextPasswordhash = (EditText) dialogView.findViewById(R.id.editTextCrudPasswordhash);
        final EditText editTextUsername = (EditText) dialogView.findViewById(R.id.editTextCrudUsername);
        final Button buttonAccept = (Button) dialogView.findViewById(R.id.buttonCrudAccept);
        final Button buttonReject = (Button) dialogView.findViewById(R.id.buttonCrudReject);

        editTextAddress.setText(address);
        editTextAdmin.setText(admin);
        editTextAge.setText(Integer.toString(age));
        editTextPasswordhash.setText(passwordhash);
        editTextUsername.setText(username);

        dialogBuilder.setTitle("User Request");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                acceptUser(userId,address,admin,age,passwordhash,username);
                alertDialog.dismiss();
            }
        });

        buttonReject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rejectUser(userId);
                alertDialog.dismiss();
            }
        });
    }

    private void rejectUser(String userId){
        databaseTempUsers = FirebaseDatabase.getInstance().getReference("tempUser").child(userId);
        databaseTempUsers.removeValue();
        Toast.makeText(this, "User has been rejected", Toast.LENGTH_LONG).show();
    }

    private void acceptUser(String userId,String address,String admin,int age,String passwordhash,String username){
        databaseUsers=FirebaseDatabase.getInstance().getReference("user");
        //String userid = databaseUsers.push().getKey();
        UserBank user = new UserBank(address, admin, age, passwordhash, userId, username);
        databaseUsers.child(userId).setValue(user);
        databaseTempUsers = FirebaseDatabase.getInstance().getReference("tempUser").child(userId);
        databaseTempUsers.removeValue();
        Toast.makeText(this, "User has been accepted", Toast.LENGTH_LONG).show();
    }
}
