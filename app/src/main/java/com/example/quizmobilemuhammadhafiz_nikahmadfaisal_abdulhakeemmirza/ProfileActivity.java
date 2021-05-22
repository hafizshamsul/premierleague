package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileActivity extends Fragment implements View.OnClickListener{
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    final String shared="sharing";
    final String loginUs="loginUsername";
    String username;

    //view objects
    private TextView textViewEmail;
   /* private Button buttonLogout;
    private Button buttonRedirect_ProfileActivity;
    private Button buttonRedirect_CrudUser;
    private Button buttonRedirect_ScoreView;*/
    private Button buttonRedirect_AddDialog;


    /*EditText editTextQuestion;
    EditText editTextOpt1;
    EditText editTextOpt2;
    EditText editTextOpt3;
    EditText editTextOpt4;
    EditText editTextAns;
    Button buttonAdd;*/

    DatabaseReference databaseQuestions;

    ListView listViewQtn;

    List<QuestionBank> questionBankList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        LoginActivity loginActivity = new LoginActivity();

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

        //initializing views
        textViewEmail = (TextView) getView().findViewById(R.id.textViewEmail);
        /*buttonLogout = (Button) getView().findViewById(R.id.buttonLogout);
        buttonRedirect_ProfileActivity = (Button) getView().findViewById(R.id.buttonRedirect_ProfileActivity);
        buttonRedirect_CrudUser = (Button) getView().findViewById(R.id.buttonRedirect_CrudUser);
        buttonRedirect_ScoreView=(Button)getView().findViewById(R.id.buttonRedirect_ScoreView);*/
        buttonRedirect_AddDialog = (Button) getView().findViewById(R.id.buttonRedirect_AddDialog);

        SharedPreferences sp=getActivity().getSharedPreferences(LoginActivity.pref,0);
        //displaying logged in user name
        //textViewEmail.setText("Welcome "+sp.getString("Username",null));



        //adding listener to button
        /*buttonLogout.setOnClickListener(this);
        buttonRedirect_ProfileActivity.setOnClickListener(this);
        buttonRedirect_CrudUser.setOnClickListener(this);
        buttonRedirect_ScoreView.setOnClickListener(this);*/
        buttonRedirect_AddDialog.setOnClickListener(this);

        databaseQuestions = FirebaseDatabase.getInstance().getReference("qtn");



        listViewQtn = (ListView) getView().findViewById(R.id.listViewQtn);

        questionBankList = new ArrayList<>();

        /*buttonAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                addQuestion();
            }
        });*/

        listViewQtn.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuestionBank questionBank = questionBankList.get(i);
                updateQuestion(questionBank.getAnswer(), questionBank.getOption1(), questionBank.getOption2(), questionBank.getOption3(), questionBank.getOption4(), questionBank.getQuestion(), questionBank.getQuestionId());
                return false;
            }
        });

        databaseQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionBankList.clear();
                for(DataSnapshot questionSnapshot : dataSnapshot.getChildren()){
                    QuestionBank questionBank = questionSnapshot.getValue(QuestionBank.class);
                    questionBankList.add(questionBank);
                }
                QuestionList adapter = new QuestionList(getActivity(), questionBankList);
                listViewQtn.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        //if logout is pressed
        /*if(view == buttonLogout){
            //logging out the user
            //firebaseAuth.signOut();
            //closing activity
            getActivity().finish();
            SharedPreferences sp=getActivity().getSharedPreferences(LoginActivity.pref,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            //starting login activity
            startActivity(new Intent(getActivity(),LoginActivity.class));
            //startActivity(new Intent(this, LoginActivity.class));
        }
        else if(view == buttonRedirect_ProfileActivity){
            //
        }
        else if(view == buttonRedirect_CrudUser){
            getActivity().finish();
            //starting login activity
            startActivity(new Intent(getActivity(), CrudUser.class));
            //startActivity(new Intent(this, LoginActivity.class));
        }else if(view==buttonRedirect_ScoreView){
            startActivity(new Intent(getActivity(),ScoreView.class));
        }*/
        if(view == buttonRedirect_AddDialog){
            addQuestion();
        }
    }



    /*private void addQuestion(){
        String question = editTextQuestion.getText().toString().trim();
        String opt1 = editTextOpt1.getText().toString().trim();
        String opt2 = editTextOpt2.getText().toString().trim();
        String opt3 = editTextOpt3.getText().toString().trim();
        String opt4 = editTextOpt4.getText().toString().trim();
        String answer= editTextAns.getText().toString().trim();

        if(!TextUtils.isEmpty(question) && !TextUtils.isEmpty(opt1) && !TextUtils.isEmpty(opt2) && !TextUtils.isEmpty(opt3)&& !TextUtils.isEmpty(opt4) &&!TextUtils.isEmpty(answer)){
            String questionId = databaseQuestions.push().getKey();
            QuestionBank qtn = new QuestionBank(answer, opt1, opt2, opt3, opt4, question, questionId);

            databaseQuestions.child(questionId).setValue(qtn);

            editTextQuestion.setText(null);
            editTextOpt1.setText(null);
            editTextOpt2.setText(null);
            editTextOpt3.setText(null);
            editTextOpt4.setText(null);
            editTextAns.setText(null);

            Toast.makeText(this,"Successful! A question has been added.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Please complete the question form.", Toast.LENGTH_LONG).show();
        }
    }*/

    private void addQuestion(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextQuestion = (EditText) dialogView.findViewById(R.id.editTextQuestion);
        final EditText editTextOpt1 = (EditText) dialogView.findViewById(R.id.editTextOpt1);
        final EditText editTextOpt2 = (EditText) dialogView.findViewById(R.id.editTextOpt2);
        final EditText editTextOpt3 = (EditText) dialogView.findViewById(R.id.editTextOpt3);
        final EditText editTextOpt4 = (EditText) dialogView.findViewById(R.id.editTextOpt4);
        final EditText editTextAnswer =(EditText) dialogView.findViewById(R.id.editTextAnswer);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.buttonAdd);

        dialogBuilder.setTitle("Adding Question");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = editTextQuestion.getText().toString().trim();

                String opt1 = editTextOpt1.getText().toString().trim();
                String opt2 = editTextOpt2.getText().toString().trim();
                String opt3 = editTextOpt3.getText().toString().trim();
                String opt4 = editTextOpt4.getText().toString().trim();
                String answer= editTextAnswer.getText().toString().trim();

                if(!TextUtils.isEmpty(question) && !TextUtils.isEmpty(opt1) && !TextUtils.isEmpty(opt2) && !TextUtils.isEmpty(opt3)&& !TextUtils.isEmpty(opt4) &&!TextUtils.isEmpty(answer)){
                    String questionId = databaseQuestions.push().getKey();
                    QuestionBank qtn = new QuestionBank(answer, opt1, opt2, opt3, opt4, question, questionId);

                    databaseQuestions.child(questionId).setValue(qtn);

                    alertDialog.dismiss();

                    editTextQuestion.setText(null);
                    editTextOpt1.setText(null);
                    editTextOpt2.setText(null);
                    editTextOpt3.setText(null);
                    editTextOpt4.setText(null);
                    editTextAnswer.setText(null);

                    scrollToBottom();

                    Toast.makeText(getActivity(),"Successful! A question has been added.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Please complete the question form.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void scrollToBottom() {
        listViewQtn.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                //listViewQtn.setSelection(adapter.getCount() - 1);
                QuestionList adapter = new QuestionList(getActivity(), questionBankList);
                listViewQtn.setSelection(adapter.getCount()-1);
            }
        });
    }

    private void updateQuestion(String answer, String opt1, String opt2, String opt3, String opt4, String question, final String questionId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName);
        final EditText editTextOpt1 = (EditText) dialogView.findViewById(R.id.editTextOpt1);
        final EditText editTextOpt2 = (EditText) dialogView.findViewById(R.id.editTextOpt2);
        final EditText editTextOpt3 = (EditText) dialogView.findViewById(R.id.editTextOpt3);
        final EditText editTextOpt4 = (EditText) dialogView.findViewById(R.id.editTextOpt4);
        final EditText editTextQuestion = (EditText) dialogView.findViewById(R.id.editTextQuestion);
        final EditText editTextAnswer = (EditText) dialogView.findViewById(R.id.editTextAnswer);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        editTextOpt1.setText(opt1);
        editTextOpt2.setText(opt2);
        editTextOpt3.setText(opt3);
        editTextOpt4.setText(opt4);
        editTextQuestion.setText(question);
        editTextAnswer.setText(answer);

        dialogBuilder.setTitle("Updating Question");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String answer = editTextAnswer.getText().toString().trim();
                String opt1 = editTextOpt1.getText().toString().trim();
                String opt2 = editTextOpt2.getText().toString().trim();
                String opt3 = editTextOpt3.getText().toString().trim();
                String opt4 = editTextOpt4.getText().toString().trim();
                String question = editTextQuestion.getText().toString().trim();

                if(TextUtils.isEmpty(question)){
                    editTextQuestion.setError("Question required");
                    return;
                }
                updateQtn(answer, opt1, opt2, opt3, opt4, question, questionId);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteQuestion(questionId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteQuestion(String questionId){
        databaseQuestions = FirebaseDatabase.getInstance().getReference("qtn").child(questionId);
        databaseQuestions.removeValue();
        Toast.makeText(getActivity(), "Question is deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateQtn(String answer, String opt1, String opt2, String opt3, String opt4, String question, String questionId){
        databaseQuestions = FirebaseDatabase.getInstance().getReference("qtn").child(questionId);
        QuestionBank questionBank = new QuestionBank(answer, opt1, opt2, opt3, opt4, question, questionId);
        databaseQuestions.setValue(questionBank);
        Toast.makeText(getActivity(), "Question Updated Successfully", Toast.LENGTH_LONG).show();
        return true;
    }
}
