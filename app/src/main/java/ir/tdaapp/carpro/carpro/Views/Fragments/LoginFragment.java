package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
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

    presenter.start(binding.editTextNumb.getText().toString());



  }

  @Override
  public void onClick(View v) {

    switch (v.getId()){

      case R.id.btn_log_in:
        if (binding.editTextNumb.getText().toString().equals("")){
          Toast.makeText(getContext(), R.string.txt_ener_number, Toast.LENGTH_SHORT).show();
        }else {
            presenter.login(binding.editTextNumb.getText().toString());
        }
        break;

      case R.id.NewAccount:

        ((MainActivity)getActivity()).onAddFragment(new SignupFragment(),R.anim.fadein,R.anim.fadeout,true,SignupFragment.TAG);

        break;

    }

  }

  @Override
  public void onPresenterStart() {

  }

  @Override
  public void onResponseReceived(ApiDefaultResponse response) {

    if (response.isResult()){
      AuthorizeDialog dialog = new AuthorizeDialog(binding.editTextNumb.getText().toString());
      dialog.show(getActivity().getSupportFragmentManager(),AuthorizeDialog.TAG);
      Toast.makeText(getContext(), response.getMessages().toString(), Toast.LENGTH_SHORT).show();
    }


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
