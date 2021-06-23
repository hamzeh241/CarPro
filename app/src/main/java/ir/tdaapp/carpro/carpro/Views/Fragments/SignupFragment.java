package ir.tdaapp.carpro.carpro.Views.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import androidx.exifinterface.media.ExifInterface;
import es.dmoral.toasty.Toasty;
import ir.tdaapp.carpro.carpro.Models.Adapters.MembersAdapter;
import ir.tdaapp.carpro.carpro.Models.Services.SignUpService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.Presenters.SignupFragmentPresenter;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.carpro.carpro.Views.Activities.MainActivity;
import ir.tdaapp.carpro.carpro.Views.Dialogs.AuthorizeDialog;
import ir.tdaapp.carpro.carpro.Views.Dialogs.ConfirmationDialog;
import ir.tdaapp.carpro.carpro.Views.Dialogs.MessageDialog;
import ir.tdaapp.carpro.carpro.databinding.FragmentSignupBinding;
import ir.tdaapp.li_utility.Codes.Replace;
import ir.tdaapp.li_utility.Codes.Validation;
import ir.tdaapp.li_volley.Enum.ResaultCode;

import static android.os.Build.VERSION.SDK_INT;

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
          userModel.setCellPhone(Replace.Number_fn_To_en(binding.edtPhone.getText().toString()));
          userModel.setEmail(binding.edtEmail.getText().toString());
          userModel.setImageUrl(imageUrl);
          presenter.signup(userModel);
        }
        break;
      case R.id.add_photo_layout:
        presenter.requestStoragePermission(getActivity());
        break;
      case R.id.img_add_photo:
        presenter.requestStoragePermission(getActivity());
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

    showErrorDialog(title, error, () ->
      presenter.signup(userModel));
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
      Toasty.success(getContext(), message).show();
      authorize();
    } else {
      Toasty.error(getContext(), message).show();
      if (response.getCode() == 302) {
        showConfirmationDialog("هشدار", message, new ConfirmationDialog.onConfirmationClick() {
          @Override
          public void onPressedYes() {
            ((MainActivity) getActivity()).onAddFragment(new LoginFragment(),
              R.anim.fadein, R.anim.fadeout, true, HomeFragment.TAG);
          }

          @Override
          public void onPressedNo() {

          }
        });
      } else if (response.getCode() == 400) {
        showConfirmationDialog("هشدار", message, new ConfirmationDialog.onConfirmationClick() {
          @Override
          public void onPressedYes() {
            ((MainActivity) getActivity()).onAddFragment(new LoginFragment(),
              R.anim.fadein, R.anim.fadeout, true, HomeFragment.TAG);
          }

          @Override
          public void onPressedNo() {

          }
        });
      }
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
    }
  }

  @Override
  public void onLoading(boolean state) {

  }

  @Override
  public void onFinish() {

  }
}
