package com.example.pelatihan3.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;

public class BrowserHelper {
    public static void bookMarkUrl(Context context, String url){
        SharedPreferences pref = context.getSharedPreferences("pelatihan3", 0);
        SharedPreferences.Editor editor = pref.edit();
        if(pref.getBoolean(url, false)){
            editor.putBoolean(url, false);
        }else {
            editor.putBoolean(url, true);
        }
        editor.commit();
    }
    public static boolean isBookmarked(Context context, String url){
        SharedPreferences preferences = context.getSharedPreferences("pelatihan3", 0);
        return preferences.getBoolean(url, true);
    }
    public static void colorMenuIcon(Context context, MenuItem item, int color){
        Drawable drawable = item.getIcon();
        if (drawable != null){
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
