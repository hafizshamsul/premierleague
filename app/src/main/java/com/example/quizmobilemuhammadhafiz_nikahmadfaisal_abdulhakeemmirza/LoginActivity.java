package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Hex;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.HashingAlgo.hashPassword;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String SessionUsername;

    //defining views
    private Button buttonSignIn;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView textViewSignup;
    final String loginUs="loginUsername";
    final String loginPs="loginPassword";
    final String loginAd="loginAdmin";
    public static final String pref="fileni";

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    SharedPreferences sp;
    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        sp=getSharedPreferences(pref, Context.MODE_PRIVATE);

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        /*if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }*/

        //initializing views
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup  = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    //method for user login
    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();


        SharedPreferences.Editor editor=sp.edit();

        editor.putString("Username",username);
        editor.putString("Password",password);
        editor.commit();

        //checking if username and passwords are empty
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the username and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("user");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exist = false;
                String storeUsername = null;
                String storePasswordhash = null;
                int storeAge = 0;
                String storeAddress = null;
                String storeAdmin = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserBank userBank = snapshot.getValue(UserBank.class);

                    String salt = "1234";
                    int iterations = 10000;
                    int keyLength = 512;
                    char[] passwordChars = password.toCharArray();
                    String passwordhash;
                    try{
                        byte[] saltBytes = salt.getBytes();
                        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
                        passwordhash = Hex.bytesToStringLowercase(hashedBytes);
                    }
                    catch(Exception e){
                        passwordhash = password;
                    }

                    if(userBank.getUsername().equals(username) && userBank.getPasswordhash().equals(passwordhash)){
                        exist = true;
                        storeUsername = userBank.getUsername();
                        storePasswordhash = userBank.getPasswordhash();
                        storeAge = userBank.getAge();
                        storeAddress = userBank.getAddress();
                        storeAdmin = userBank.getAdmin();
                    }
                }
                if(exist == true){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Login successful!",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), "Username: "+ storeUsername +", Password: "+ storePasswordhash + ", Age: " + storeAge + "Address: "+ storeAddress + ", Admin: "+ storeAdmin,Toast.LENGTH_LONG).show();

                    finish();



                    if(storeAdmin.equals("y")){
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                    else if(storeAdmin.equals("n")){
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                    }
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Incorrect username or password",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                //logging in the user
        /*firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                                SessionUsername = "admin";
                                //start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }
                            else{
                                //start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }
                        }
                    }
        });*/

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }

        if(view == textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity_MuhammadHafiz_NikAhmadFaisal_AbdulHakeemMirza.class));
        }
    }

}
