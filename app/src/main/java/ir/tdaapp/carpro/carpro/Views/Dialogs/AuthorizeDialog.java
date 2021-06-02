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
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.databinding.DialogAuthorizeBinding;

public class AuthorizeDialog extends DialogFragment implements AuthorizeDialogService, View.OnClickListener {

    public static final String TAG = "AuthorizeDialog";

    AuthorizeDialogPresenter presenter;

    DialogAuthorizeBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DialogAuthorizeBinding.inflate(inflater, container, false);


        implement();
        return binding.getRoot();
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
