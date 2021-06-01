package ir.tdaapp.carpro.carpro.Views.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Fragments.FragmentLogIn;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onAddFragment(new HomeFragment(),R.anim.fadein,R.anim.fadein,true,HomeFragment.TAG);
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

}