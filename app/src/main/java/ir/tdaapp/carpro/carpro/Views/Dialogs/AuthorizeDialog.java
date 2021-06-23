package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.SimpleTimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_User;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.AuthorizeRepository;
import ir.tdaapp.carpro.carpro.Models.Services.AuthorizeDialogService;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.AuthorizeDialogPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.LoginFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.UserConfigurationMessageFragment;
import ir.tdaapp.carpro.carpro.databinding.DialogAuthorizeBinding;
import ir.tdaapp.li_utility.Codes.Replace;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public class AuthorizeDialog extends DialogFragment implements AuthorizeDialogService, View.OnClickListener {

  public static final String TAG = "AuthorizeDialog";
  AuthorizeDialogPresenter presenter;
  DialogAuthorizeBinding binding;

  String number;
  String password;

  public AuthorizeDialog(String number) {
    this.number = number;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    binding = DialogAuthorizeBinding.inflate(inflater, container, false);
    setCancelable(false);
    implement();
    return binding.getRoot();
  }

  private void implement() {
    presenter = new AuthorizeDialogPresenter(getContext(), this);
    password = binding.edtCode.getText().toString();
    binding.btnSubmit.setOnClickListener(this);

    binding.edtCode.setOnKeyListener((v, keyCode, event) -> {
      // If the event is a key-down event on the "enter" button
      if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
        (keyCode == KeyEvent.KEYCODE_ENTER)) {
        // Perform action on key press
        binding.btnSubmit.performClick();
        return true;
      }
      return false;
    });
  }

  @Override
  public int getTheme() {
    return R.style.RoundedCornersDialog;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnSubmit:
        presenter.authorize(Replace.Number_fn_To_en(number), Replace.Number_fn_To_en(binding.edtCode.getText().toString()));
        break;
    }
  }

  @Override
  public void onLoading(boolean state) {
    binding.btnSubmit.setEnabled(!state);
    binding.mkLoader.setVisibility(state ? View.VISIBLE : View.GONE);
    binding.textView7.setVisibility(state ? View.GONE : View.VISIBLE);
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

    ErrorDialog dialog = new ErrorDialog(title, error, () -> {
    });

    dialog.show(getActivity().getSupportFragmentManager(), ErrorDialog.TAG);
  }

  @Override
  public void onDataReceived(ApiDefaultResponse response) {

    String message = "";
    for (int i = 0; i < response.getMessages().size(); i++)
      message = new StringBuilder().append(response
        .getMessages()
        .get(i))
        .append(response.getMessages().size() > 1 ? "\n" : "")
        .toString();

    if (response.isResult()) {
      Toasty.success(getContext(), message).show();
      ((MainActivity) getActivity()).getTbl_user().add(response.getCode());
      ((MainActivity) getActivity()).onAddFragment(new HomeFragment(), 0, 0, false, HomeFragment.TAG);
      this.dismiss();
    } else {
      Toasty.error(getContext(), message).show();
    }
  }

  @Override
  public void onPresenterStart() {

  }
}
