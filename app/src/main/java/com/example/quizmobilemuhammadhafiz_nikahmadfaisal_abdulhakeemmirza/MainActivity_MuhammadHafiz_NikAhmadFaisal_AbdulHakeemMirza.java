package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.Hex;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKeyFactory;

import static com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza.HashingAlgo.hashPassword;

public class MainActivity_MuhammadHafiz_NikAhmadFaisal_AbdulHakeemMirza extends AppCompatActivity implements View.OnClickListener {
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextAge;
    private EditText editTextAddress;
    private Button buttonRegister;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;


    DatabaseReference databaseUsers;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    HashingAlgo hashingAlgo = new HashingAlgo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();


        //if getCurrentUser does not returns null
        /*if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainActivity_MuhammadHafiz_NikAhmadFaisal_AbdulHakeemMirza.class));
        }*/

        //initializing views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAge = findViewById(R.id.editTextAge);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewSignin = findViewById(R.id.textViewSignIn);

        buttonRegister.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        databaseUsers = FirebaseDatabase.getInstance().getReference("tempUser");
    }

    private void addUser(){
        //getting email and password from edit texts
        final String username = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        int age = Integer.parseInt(editTextAge.getText().toString());
        String address = editTextAddress.getText().toString().trim();
        String admin;

        String salt = "1234";
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
        String passwordhash = Hex.bytesToStringLowercase(hashedBytes);

        if(passwordhash.equals(hashingAlgo.getAdminhash())){
            admin = "y";
        }
        else{
            admin = "n";
        }

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(Integer.toString(age)) && !TextUtils.isEmpty(address)){
            final String userId = databaseUsers.push().getKey();
            final UserBank user = new UserBank(address, admin, age, passwordhash, userId, username);

            databaseUsers.child(userId).setValue(user);
            Toast.makeText(MainActivity_MuhammadHafiz_NikAhmadFaisal_AbdulHakeemMirza.this,"Successful! A user has been added.", Toast.LENGTH_LONG).show();


            finish();


            if(admin.equals("n")){
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
            else if(admin.equals("y")){
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }

        }
        else{
            Toast.makeText(this, "Please complete the registration form.", Toast.LENGTH_LONG).show();
        }
    }

    /*private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity_MuhammadHafiz_NikAhmadFaisal_AbdulHakeemMirza.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }*/

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){

            addUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}