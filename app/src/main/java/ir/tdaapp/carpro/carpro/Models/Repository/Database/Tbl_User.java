package ir.tdaapp.carpro.carpro.Models.Repository.Database;

import android.content.Context;
import android.content.SharedPreferences;


import static android.content.Context.MODE_PRIVATE;

public class Tbl_User {

  public static final String SHARED_PREFERENCE_TAG = "CarProShared";
  public static final String SHARED_PREFERENCE_USERID = "CarProShared";

  public void add(Context context, int userId) {
    SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_TAG, MODE_PRIVATE).edit();
    editor.putInt(SHARED_PREFERENCE_USERID, userId);
    editor.apply();
  }

  public boolean hasAccount(Context context) {

    if (getUserId(context) > 0)
      return true;
    return false;

  }

  public int getUserId(Context context) {

    SharedPreferences editor = context.getSharedPreferences(SHARED_PREFERENCE_TAG, MODE_PRIVATE);
    int userId = editor.getInt(SHARED_PREFERENCE_USERID, 0);
    return userId;

  }

}
