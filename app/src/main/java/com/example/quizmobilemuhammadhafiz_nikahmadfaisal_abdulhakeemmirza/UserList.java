package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserList extends ArrayAdapter<UserBank> {
    private Activity context;
    private List<UserBank> userBankList;

    public UserList(Activity context, List<UserBank> userBankList){
        super(context, R.layout.activity_userlist, userBankList);
        this.context = context;
        this.userBankList = userBankList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_userlist, null, true);

        TextView textViewUsername = (TextView) listViewItem.findViewById(R.id.textViewUsername);
        TextView textViewPasswordhash = (TextView) listViewItem.findViewById(R.id.textViewPasswordhash);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);
        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.textViewAge);
        TextView textViewAdmin = (TextView) listViewItem.findViewById(R.id.textViewAdmin);

        UserBank userBank = userBankList.get(position);

        textViewUsername.setText("Username: " + userBank.getUsername());
        textViewPasswordhash.setText("Password Hash: " + userBank.getPasswordhash());
        textViewAddress.setText("Address: " + userBank.getAddress());
        textViewAge.setText("Age: " + userBank.getAge());
        textViewAdmin.setText("Admin: " + userBank.getAdmin());

        return listViewItem;
    }
}



























