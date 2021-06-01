package ir.tdaapp.carpro.carpro.Views.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.SimpleTimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.tdaapp.carpro.carpro.Models.Services.AuthorizeDialogService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Presenters.AuthorizeDialogPresenter;

public class AuthorizeDialog extends DialogFragment implements AuthorizeDialogService, View.OnClickListener {

  AuthorizeDialogPresenter presenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    implement();
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  private void implement() {
    presenter = new AuthorizeDialogPresenter(getContext(), this);
  }

  @Override
  public void onClick(View v) {

  }

  @Override
  public void onLoading(boolean state) {

  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onDataReceived(ApiDefaultResponse response) {

  }

  @Override
  public void onPresenterStart() {

  }
}
