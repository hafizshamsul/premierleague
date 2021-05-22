package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserTempList extends ArrayAdapter<UserBank> {

    private Activity context;
    private List<UserBank> userTempBankList;

    public UserTempList(Activity context, List<UserBank> userBankList){
        super(context, R.layout.activity_userlist, userBankList);
        this.context = context;
        this.userTempBankList = userBankList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_temp_list, null, true);

        TextView textViewUsername = (TextView) listViewItem.findViewById(R.id.textViewTempUsername);
        TextView textViewPasswordhash = (TextView) listViewItem.findViewById(R.id.textViewTempPasswordhash);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewTempAddress);
        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.textViewTempAge);
        TextView textViewAdmin = (TextView) listViewItem.findViewById(R.id.textViewTempAdmin);

        UserBank userBank = userTempBankList.get(position);

        textViewUsername.setText("Username: " + userBank.getUsername());
        textViewPasswordhash.setText("Password Hash: " + userBank.getPasswordhash());
        textViewAddress.setText("Address: " + userBank.getAddress());
        textViewAge.setText("Age: " + userBank.getAge());
        textViewAdmin.setText("Admin: " + userBank.getAdmin());

        return listViewItem;
    }
}
