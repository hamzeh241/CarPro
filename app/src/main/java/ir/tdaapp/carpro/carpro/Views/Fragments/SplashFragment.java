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

import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.UserEnabledStateRepository;
import ir.tdaapp.carpro.carpro.Models.Services.SplashFragmentService;
import ir.tdaapp.carpro.carpro.Presenters.SplashPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.databinding.FragmentSplashBinding;

public class SplashFragment extends BaseFragment implements SplashFragmentService {


    public static final String TAG = "SplashFragment";

    CountDownTimer timer;
    SplashPresenter presenter;

    FragmentSplashBinding binding;

    int userId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);

        implement();
        DateTime();

        return binding.getRoot();
    }

    private void implement() {
        presenter = new SplashPresenter(getContext(), this);
        presenter.start();
        userId = ((MainActivity) getActivity()).getTbl_user().getUserId();
    }


    public void DateTime() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation aniFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_splash);
                binding.showItem.startAnimation(aniFade);
            }
        }, 500);

        timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

//                if (((MainActivity) getActivity()).getTbl_user().hasAccount()) {
//                    presenter.getStatusUser(((MainActivity) getActivity()).getTbl_user().getUserId());
//
//                } else if (((MainActivity)getActivity()).getTbl_user().getUserId() != 0){
////                    ((MainActivity) getActivity()).onAddFragment(new HomeFragment(), R.anim.fadein, R.anim.fadeout, false, HomeFragment.TAG);
//
//                }else {
//                    ((MainActivity) getActivity()).onAddFragment(new LoginFragment(),  R.anim.fadein, R.anim.fadeout, false, LoginFragment.TAG);
//
//                }



                if (userId != 0) {
                    ((MainActivity) getActivity()).onAddFragment(new HomeFragment(), R.anim.fadein, 0, false, HomeFragment.TAG);
                } else {
                    ((MainActivity) getActivity()).onAddFragment(new LoginFragment(), R.anim.fadein, 0, false, LoginFragment.TAG);
                }
            }

        }.start();
    }

    @Override
    public void onPresenterStart() {

    }

    @Override
    public void onResponseRecive(String status) {
    }

    @Override
    public void onLoading(boolean load) {

    }

    @Override
    public void onError(String message) {

    }


    @Override
    public void onFinish() {

    }
}
