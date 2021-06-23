package ir.tdaapp.carpro.carpro.Views.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.DBAdapter;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.LoginFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.SplashFragment;

import static android.os.Build.VERSION.SDK_INT;

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

  public void goBackToLogin() {
    new Thread(() -> {
      List<Fragment> fragments = getSupportFragmentManager().getFragments();

      if (!doesLoginExistBackstack())
        onAddFragment(new LoginFragment(),
          R.anim.fadein, R.anim.fadeout,
          false, LoginFragment.TAG);
      else for (Fragment fragment : fragments)
        if (!(fragment instanceof LoginFragment))
          getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }).start();
  }

  private boolean doesLoginExistBackstack() {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();

    for (Fragment fragment : fragments)
      if (fragment instanceof LoginFragment)
        return true;

    return false;
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