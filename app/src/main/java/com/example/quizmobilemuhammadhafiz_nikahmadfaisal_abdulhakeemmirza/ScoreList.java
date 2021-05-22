package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreList extends ArrayAdapter<ScoreBank> {


    Activity context;
    List<ScoreBank> scoreBankList;
    public ScoreList(Activity context, List<ScoreBank> scoreBankList) {
        super(context, R.layout.activity_score_list,scoreBankList);
        this.context=context;
        this.scoreBankList=scoreBankList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem =inflater.inflate(R.layout.activity_score_list,null,true);

        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewPoint = (TextView) listViewItem.findViewById(R.id.textViewPoint);
        TextView textViewScore = (TextView) listViewItem.findViewById(R.id.textViewScore);
        TextView textViewDateTime = (TextView) listViewItem.findViewById(R.id.textViewDateTime);

        ScoreBank sb=scoreBankList.get(position);

        textViewEmail.setText("Username : "+sb.getUserEmail());
        textViewPoint.setText("Point : "+sb.getPoint());
        textViewScore.setText("Score : "+sb.getUserScore());
        textViewDateTime.setText("Date & Time: "+sb.getDatetime());

        return listViewItem;
    }
}
