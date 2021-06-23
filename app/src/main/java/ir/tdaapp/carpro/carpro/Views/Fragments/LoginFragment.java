package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
import ir.tdaapp.carpro.carpro.Models.Services.LoginFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.LoginFragmentPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Dialogs.AuthorizeDialog;
import ir.tdaapp.carpro.carpro.Views.Dialogs.ErrorDialog;
import ir.tdaapp.carpro.carpro.databinding.FragmentLogInBinding;
import ir.tdaapp.li_utility.Codes.Replace;
import ir.tdaapp.li_volley.Enum.ResaultCode;

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

    presenter.start();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_log_in:
        if (binding.editTextNumb.getText().toString().equalsIgnoreCase("")) {
          Toast.makeText(getContext(), R.string.txt_ener_number, Toast.LENGTH_SHORT).show();
        } else {
          presenter.login(Replace.Number_fn_To_en(binding.editTextNumb.getText().toString()));
        }
        break;
      case R.id.NewAccount:
        ((MainActivity) getActivity()).onAddFragment(new SignupFragment(), R.anim.fadein, R.anim.fadeout, true, SignupFragment.TAG);
        break;
    }
  }

  @Override
  public void onPresenterStart() {

  }

  @Override
  public void onResponseReceived(ApiDefaultResponse response) {

    String message = "";
    for (int i = 0; i < response.getMessages().size(); i++)
      message = new StringBuilder().append(response
        .getMessages()
        .get(i))
        .append(response.getMessages().size() > 1 ? "\n" : "")
        .toString();

    if (response.isResult()) {
      AuthorizeDialog dialog = new AuthorizeDialog(binding.editTextNumb.getText().toString());
      dialog.show(getActivity().getSupportFragmentManager(), AuthorizeDialog.TAG);
      Toasty.success(getContext(), message).show();
    } else Toasty.error(getContext(), message).show();


  }

  @Override
  public void onError(ResaultCode code) {
    String error = "";
    String title = "";

    switch (code) {
      case TimeoutError:
        error = getString(R.string.timeout_error);
        title = getString(R.string.timeout_error_title);
        break;
      case NetworkError:
        error = getString(R.string.network_error);
        title = getString(R.string.network_error_title);
        break;
      case ServerError:
        error = getString(R.string.server_error);
        title = getString(R.string.server_error_title);
        break;
      case ParseError:
      case Error:
        title = getString(R.string.unknown_error_title);
        error = getString(R.string.unknown_error);
        break;
    }

    showErrorDialog(title, error, () -> {
      presenter.login(Replace.Number_fn_To_en(binding.editTextNumb.getText().toString()));
    });
  }

  @Override
  public void onLoading(boolean state) {
    TransitionManager.beginDelayedTransition(binding.getRoot(), new Slide(Gravity.TOP));
    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
  }
}
