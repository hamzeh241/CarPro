package ir.tdaapp.carpro.carpro.Views.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import ir.tdaapp.carpro.carpro.Models.Adapters.DBAdapter;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

  private Tbl_User tbl_user;
  private DBAdapter dbAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    dbAdapter = new DBAdapter(this);

    implement();

    onAddFragment(new SplashFragment(), 0, 0, false, HomeFragment.TAG);
  }

  private void implement() {

    tbl_user = new Tbl_User(MainActivity.this);
  }

  public Tbl_User getTbl_user() {
    return tbl_user;
  }


  public void onAddFragment(Fragment fragment,
                            @AnimatorRes @AnimRes int animEnter,
                            @AnimatorRes @AnimRes int animExit,
                            boolean backStack, String fragmentTag) {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    if (animEnter != 0 & animExit != 0) {
      transaction.setCustomAnimations(animEnter, animExit, animEnter, animExit);
    }
    transaction.add(R.id.frame_main_container, fragment, fragmentTag);

    if (backStack) {
      transaction.addToBackStack(null);
    }
    transaction.commit();
  }

  public DBAdapter getDbAdapter() {
    return dbAdapter;
  }
}