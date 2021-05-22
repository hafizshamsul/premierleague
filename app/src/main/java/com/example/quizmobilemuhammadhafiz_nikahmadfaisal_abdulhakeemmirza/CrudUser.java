package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CrudUser extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewEmail;
    private Button buttonLogout;
    private Button buttonRedirect_ProfileActivity;
    private Button buttonRedirect_CrudUser;
    private Button buttonRedirect_ScoreView;
    private Button buttonRedirect_AddUserDialog;
    private Button buttonRedirect_Registered;
    private Button buttonRedirect_Pending;

    DatabaseReference databaseUsers;

    ListView listViewUser;

    List<UserBank> userBankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruduser);

        //LoginActivity loginActivity = new LoginActivity();

        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonRedirect_ProfileActivity = (Button) findViewById(R.id.buttonRedirect_ProfileActivity);
        buttonRedirect_CrudUser = (Button) findViewById(R.id.buttonRedirect_CrudUser);
        buttonRedirect_ScoreView = (Button) findViewById(R.id.buttonRedirect_ScoreView);
        buttonRedirect_AddUserDialog = (Button) findViewById(R.id.buttonRedirect_AddUserDialog);
        buttonRedirect_Registered = (Button) findViewById(R.id.buttonRedirect_Registered);
        buttonRedirect_Pending = (Button) findViewById(R.id.buttonRedirect_Pending);

        buttonLogout.setOnClickListener(this);
        buttonRedirect_ProfileActivity.setOnClickListener(this);
        buttonRedirect_CrudUser.setOnClickListener(this);
        buttonRedirect_ScoreView.setOnClickListener(this);
        buttonRedirect_AddUserDialog.setOnClickListener(this);
        buttonRedirect_Registered.setOnClickListener(this);
        buttonRedirect_Pending.setOnClickListener(this);

        databaseUsers = FirebaseDatabase.getInstance().getReference("user");

        listViewUser = (ListView) findViewById(R.id.listViewUser);

        userBankList = new ArrayList<>();

        listViewUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserBank userBank = userBankList.get(i);
                updateUser(userBank.getAddress(), userBank.getAdmin(), userBank.getAge(), userBank.getPasswordhash(), userBank.getUserId(), userBank.getUsername());
                return false;
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
        else if(view == buttonRedirect_AddUserDialog){
            addUser();
        }
        else if(view == buttonRedirect_Registered){
            //
        }
        else if(view == buttonRedirect_Pending){
            finish();
            startActivity(new Intent(this, rejectApproveUser.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userBankList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    UserBank userBank = userSnapshot.getValue(UserBank.class);
                    userBankList.add(userBank);
                }
                UserList adapter = new UserList(CrudUser.this, userBankList);
                listViewUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addUser(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.adduser_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
        final EditText editTextAdmin = (EditText) dialogView.findViewById(R.id.editTextAdmin);
        final EditText editTextAge = (EditText) dialogView.findViewById(R.id.editTextAge);
        final EditText editTextPasswordhash = (EditText) dialogView.findViewById(R.id.editTextPasswordhash);
        final EditText editTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
        final Button buttonAddUser = (Button) dialogView.findViewById(R.id.buttonAddUser);

        dialogBuilder.setTitle("Adding User");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = editTextAddress.getText().toString().trim();
                String admin = editTextAdmin.getText().toString().trim();
                int age;
                try{
                    String s_age= editTextAge.getText().toString().trim();
                    age = Integer.parseInt(s_age);
                }
                catch(NumberFormatException e){
                    age = 0;
                }
                String passwordhash = editTextPasswordhash.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();

                if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(admin) && age>0 && !TextUtils.isEmpty(passwordhash)&& !TextUtils.isEmpty(username)){
                    String userId = databaseUsers.push().getKey();
                    UserBank user = new UserBank(address, admin, age, passwordhash, userId, username);

                    databaseUsers.child(userId).setValue(user);

                    editTextAddress.setText(null);
                    editTextAdmin.setText(null);
                    editTextAge.setText(null);
                    editTextPasswordhash.setText(null);
                    editTextUsername.setText(null);

                    Toast.makeText(getApplicationContext(),"Successful! A user has been added.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please complete the user form.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateUser(String address, String admin, int age, String passwordhash, final String userId, String username){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updateuser_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName);
        final EditText editTextAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
        final EditText editTextAdmin = (EditText) dialogView.findViewById(R.id.editTextAdmin);
        final EditText editTextAge = (EditText) dialogView.findViewById(R.id.editTextAge);
        final EditText editTextPasswordhash = (EditText) dialogView.findViewById(R.id.editTextPasswordhash);
        final EditText editTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        editTextAddress.setText(address);
        editTextAdmin.setText(admin);
        editTextAge.setText(Integer.toString(age));
        editTextPasswordhash.setText(passwordhash);
        editTextUsername.setText(username);

        dialogBuilder.setTitle("Updating User");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String address = editTextAddress.getText().toString().trim();
                String admin = editTextAdmin.getText().toString().trim();
                int age;
                try{
                    String s_age= editTextAge.getText().toString().trim();
                    age = Integer.parseInt(s_age);
                }
                catch(NumberFormatException e){
                    age = 0;
                }
                String passwordhash = editTextPasswordhash.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    editTextUsername.setError("Username required");
                    return;
                }
                updateUsr(address, admin, age, passwordhash, userId, username);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteUser(userId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteUser(String userId){
        databaseUsers = FirebaseDatabase.getInstance().getReference("user").child(userId);
        databaseUsers.removeValue();
        Toast.makeText(this, "User is deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateUsr(String address, String admin, int age, String passwordhash, String userId, String username){
        databaseUsers = FirebaseDatabase.getInstance().getReference("user").child(userId);
        UserBank userBank = new UserBank(address, admin, age, passwordhash, userId, username);
        databaseUsers.setValue(userBank);
        Toast.makeText(this, "User Updated Successfully", Toast.LENGTH_LONG).show();
        return true;
    }
}
