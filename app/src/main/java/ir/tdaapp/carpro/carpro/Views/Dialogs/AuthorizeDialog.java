package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.os.Bundle;
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
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.AuthorizeDialogPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Fragments.HomeFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.LoginFragment;
import ir.tdaapp.carpro.carpro.Views.Fragments.UserConfigurationMessageFragment;
import ir.tdaapp.carpro.carpro.databinding.DialogAuthorizeBinding;

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

    implement();
    return binding.getRoot();
  }

  private void implement() {
    presenter = new AuthorizeDialogPresenter(getContext(), this);
    password = binding.etRecivedCode.getText().toString();
    presenter.start(number, password);
    binding.btnConfiguration.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.btn_configuration:
        presenter.authorize(number, binding.etRecivedCode.getText().toString());
        break;
    }


  }

  @Override
  public void onLoading(boolean state) {

  }

  @Override
  public void onError(String message) {

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
