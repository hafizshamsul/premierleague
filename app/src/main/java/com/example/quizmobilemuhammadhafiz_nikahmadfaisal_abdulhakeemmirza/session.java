package com.example.quizmobilemuhammadhafiz_nikahmadfaisal_abdulhakeemmirza;

import android.content.Context;
import android.content.SharedPreferences;

public class session {
    final static String shared="sharing";
    final static String loginUs="loginUsername";

    public static String readShared(Context ctx,String setName,String defaultVal){
        SharedPreferences sp=ctx.getSharedPreferences(shared,Context.MODE_PRIVATE);
        return sp.getString(shared,defaultVal);
    }

    public static void saveShared(Context ctx,String setName,String defaultVal){
        SharedPreferences sp=ctx.getSharedPreferences(shared,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(setName,defaultVal);
        editor.apply();
    }

    public static void sharedSave(Context ctx,String username){
        SharedPreferences sp=ctx.getSharedPreferences(loginUs,0);
        SharedPreferences.Editor prefEdit=sp.edit();
        prefEdit.commit();
    }
}
