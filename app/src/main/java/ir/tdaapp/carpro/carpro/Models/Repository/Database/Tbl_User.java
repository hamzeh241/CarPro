package ir.tdaapp.carpro.carpro.Models.Repository.Database;

import android.content.Context;
import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

public class Tbl_User {

  public static final String SHARED_PREFERENCE_TAG = "CarProShared";
  public static final String SHARED_PREFERENCE_USER_ID = "CarProShared";

  private Context context;

  public Tbl_User(Context context) {
    this.context = context;
  }

  public void add(int userId) {
    SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_TAG, MODE_PRIVATE).edit();
    editor.putInt(SHARED_PREFERENCE_USER_ID, userId);
    editor.apply();
  }

  public void  removeUser(){
    SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_TAG,MODE_PRIVATE).edit();
    editor.remove(SHARED_PREFERENCE_USER_ID);
    editor.apply();
  }

  public boolean hasAccount() {

    if (getUserId() > 0)
      return true;
    return false;

  }

  public int getUserId() {

    SharedPreferences editor = context.getSharedPreferences(SHARED_PREFERENCE_TAG, MODE_PRIVATE);
    int userId = editor.getInt(SHARED_PREFERENCE_USER_ID, 0);
    return userId;

  }

}
