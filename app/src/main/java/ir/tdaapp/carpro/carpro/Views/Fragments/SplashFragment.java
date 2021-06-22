package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment {


    public static final String TAG = "SplashFragment";

    CountDownTimer timer;

    FragmentSplashBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);

        setTimer();

        return binding.getRoot();
    }

    public void setTimer() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Animation aniFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_splash);
            binding.showItem.startAnimation(aniFade);
        }, 500);

        timer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (((MainActivity) getActivity()).getTbl_user().hasAccount()) {
                    ((MainActivity) getActivity()).onAddFragment(new HomeFragment(), R.anim.fadein, R.anim.fadeout, false, HomeFragment.TAG);
                } else {
                    ((MainActivity) getActivity()).onAddFragment(new LoginFragment(), R.anim.fadein, R.anim.fadeout, false, LoginFragment.TAG);
                }
            }

        }.start();
    }
}
