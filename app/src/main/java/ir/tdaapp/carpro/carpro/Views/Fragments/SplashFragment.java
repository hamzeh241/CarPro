package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment {


    public static final String TAG = "SplashFragment";

    LinearLayout showItem;
    CountDownTimer timer;
    Button btn_Reload;

    FragmentSplashBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentSplashBinding.inflate(inflater,container,false);

        DateTime();
        return binding.getRoot();
    }

    public void findView(View view){
        showItem = view.findViewById(R.id.showItem);
        btn_Reload = view.findViewById(R.id.btn_Reload);
    }


    public void DateTime() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation aniFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_splash);
                showItem.startAnimation(aniFade);
            }
        }, 500);

        timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                ((MainActivity)getActivity()).onAddFragment(new HomeFragment(),0,0,false,HomeFragment.TAG);

            }

        }.start();
    }
}
