package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.tdaapp.carpro.carpro.Models.Services.LoginFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.LoginFragmentPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Dialogs.AuthorizeDialog;
import ir.tdaapp.carpro.carpro.databinding.FragmentLogInBinding;

public class LoginFragment extends BaseFragment implements LoginFragmentService, View.OnClickListener {

  public static final String TAG = "FragmentLogIn";

  FragmentLogInBinding binding;
  LoginFragmentPresenter presenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = FragmentLogInBinding.inflate(inflater, container, false);

    implement();


    return binding.getRoot();
  }

  private void implement() {
    presenter = new LoginFragmentPresenter(getContext(), this);
    binding.btnLogIn.setOnClickListener(this);
    binding.NewAccount.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    switch (v.getId()){

      case R.id.btn_log_in:
        AuthorizeDialog dialog = new AuthorizeDialog();
        dialog.show(getActivity().getSupportFragmentManager(),AuthorizeDialog.TAG);
        break;

      case R.id.NewAccount:

        ((MainActivity)getActivity()).onAddFragment(new SignupFragment(),0,0,true,SignupFragment.TAG);

        break;

    }

  }

  @Override
  public void onPresenterStart() {

  }

  @Override
  public void onResponseReceived(ApiDefaultResponse response) {

  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onLoading(boolean state) {
//    TransitionManager.beginDelayedTransition(binding.getRoot(), new Slide(Gravity.TOP));
//    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
  }
}
