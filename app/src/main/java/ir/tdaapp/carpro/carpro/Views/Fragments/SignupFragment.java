package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.MembersAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.SignUpService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.AddItemPhotoModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Presenters.SignupFragmentPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Dialogs.AuthorizeDialog;
import ir.tdaapp.carpro.carpro.Views.Dialogs.MessageDialog;
import ir.tdaapp.carpro.carpro.databinding.FragmentSignupBinding;
import ir.tdaapp.li_utility.Codes.Validation;

public class SignupFragment extends BaseFragment implements View.OnClickListener, SignUpService {

    public static final String TAG = "SignupFragment";

    FragmentSignupBinding binding;
    SignupFragmentPresenter presenter;
    UserModel userModel;
    MembersAdapter adapter;
    MessageDialog uploading;
    MessageDialog sending;
    String imageUrl;
    boolean isEmptyUrl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        implement();
        return binding.getRoot();
    }

    private void implement() {

        presenter = new SignupFragmentPresenter(getContext(), this);
        presenter.start();
        binding.btnSignup.setOnClickListener(this);
        binding.imgBackSignup.setOnClickListener(this);
        binding.addPhotoLayout.setOnClickListener(this);
        binding.imgAddPhoto.setOnClickListener(this);
        adapter = new MembersAdapter(getContext());
        uploading = new MessageDialog("در حال آپلود عکس...", false);
        sending = new MessageDialog("در حال ثبت عکس...", false);
        userModel = new UserModel();


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_signup:
                if (checkValidation()) {
                    userModel.setName(binding.edtName.getText().toString());
                    userModel.setCellPhone(binding.edtPhone.getText().toString());
                    userModel.setEmail(binding.edtEmail.getText().toString());
                    userModel.setImageUrl(imageUrl);
                    presenter.signup(userModel);
                    presenter.login(userModel.getCellPhone());
                    onLoading(true);
                }
                break;
            case R.id.add_photo_layout:
                presenter.requestStrogePermistion(getActivity());
                break;
            case R.id.img_add_photo:
                presenter.requestStrogePermistion(getActivity());
                break;
            case R.id.img_back_signup:
                getActivity().onBackPressed();
                break;
        }

    }

    public void authorize() {
        AuthorizeDialog authorizeDialog = new AuthorizeDialog(userModel.getCellPhone());
        authorizeDialog.show(getActivity().getSupportFragmentManager(), AuthorizeDialog.TAG);
    }

    private boolean checkValidation() {

        boolean a = Validation.Required(binding.edtName, "نام خود را وارد کنید");
        boolean b = Validation.Required(binding.edtPhone, "شماره موبایل نباید خالی باشد");
        boolean c = checkImageUploaded();
        return a && b && c;
    }

    @Override
    public void onPresenterStart() {

    }

    @Override
    public void onError(String result) {

    }

    @Override
    public void onResponseReceived(ApiDefaultResponse response) {

    }

    @Override
    public void onResponseReciveCode(ApiDefaultResponse response) {
        if (response.isResult()) {
            onLoading(false);
            Toast.makeText(getContext(), response.getMessages().toString(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onStoragePermissionGranted() {
        presenter.openImageSelector(getActivity());
    }

    @Override
    public void onStoragePermissionDenied() {
        Toast.makeText(getContext(), R.string.storage_permisison_needed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImagesUploaded(String filename, Uri uri) {
        Glide.with(getContext())
                .load(uri)
                .into(binding.imgUserPhoto);
        String FileName = filename.replace("\"", "");
        imageUrl = FileName;
        userModel.setImageUrl(FileName);

    }

    @Override
    public void onImageUploading(boolean loading) {
        if (loading) {
            uploading.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
        } else {
            uploading.dismiss();
        }

    }


    public boolean checkImageUploaded() {

        isEmptyUrl = (imageUrl == null || imageUrl.length() == 0);
        if (!isEmptyUrl) {
            return true;
        } else {
            Toasty.error(getContext(), R.string.no_photo_selected, Toasty.LENGTH_SHORT, false).show();
            return false;
        }

    }

    @Override
    public void onDataSendingState(boolean loading) {

        if (loading) {
            sending.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
        } else {
            sending.dismiss();
            authorize();
        }
    }

    @Override
    public void onLoading(boolean state) {

    }

    @Override
    public void onFinish() {

    }
}
