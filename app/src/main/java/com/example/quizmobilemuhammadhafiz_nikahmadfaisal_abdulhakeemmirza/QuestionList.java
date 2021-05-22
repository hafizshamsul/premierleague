package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QuestionList extends ArrayAdapter<QuestionBank> {
    private Activity context;
    private List<QuestionBank> questionBankList;

    public QuestionList(Activity context, List<QuestionBank> questionBankList){
        super(context, R.layout.activity_qtnlist, questionBankList);
        this.context = context;
        this.questionBankList = questionBankList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_qtnlist, null, true);

        TextView textViewQuestion = (TextView) listViewItem.findViewById(R.id.textViewQuestion);
        TextView textViewOpt1 = (TextView) listViewItem.findViewById(R.id.textViewOpt1);
        TextView textViewOpt2 = (TextView) listViewItem.findViewById(R.id.textViewOpt2);
        TextView textViewOpt3 = (TextView) listViewItem.findViewById(R.id.textViewOpt3);
        TextView textViewOpt4 = (TextView) listViewItem.findViewById(R.id.textViewOpt4);
        TextView textViewAnswer=(TextView) listViewItem.findViewById(R.id.textViewAnswer);

        QuestionBank questionBank = questionBankList.get(position);

        textViewQuestion.setText(questionBank.getQuestion());
        textViewOpt1.setText("A. " + questionBank.getOption1());
        textViewOpt2.setText("B. " + questionBank.getOption2());
        textViewOpt3.setText("C. " + questionBank.getOption3());
        textViewOpt4.setText("D. " + questionBank.getOption4());
        textViewAnswer.setText("Answer : "+questionBank.getAnswer());

        //int white = Color.parseColor("#fff");
        //listViewItem.setBackgroundColor(white) ;

        return listViewItem;
    }
}



























