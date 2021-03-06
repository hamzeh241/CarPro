package ir.tdaapp.carpro.carpro.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.FileManger;
import ir.tdaapp.carpro.carpro.GetRandom;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.BaseRepository;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.SignupRepository;
import ir.tdaapp.carpro.carpro.Models.Services.SignUpService;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Models.Utilities.SaveImageToMob;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.li_image.ImagesCodes.CompressImage;

public class SignupFragmentPresenter {

  Context context;
  SignUpService service;
  SignupRepository repository;
  Disposable disposable;

  public SignupFragmentPresenter(Context context, SignUpService service) {
    this.context = context;
    this.service = service;
    repository = new SignupRepository();
  }

  public void start() {
    service.onPresenterStart();
  }


  public void signup(UserModel model) {
    service.onLoading(false);
    service.onDataSendingState(true);
    JSONObject object = new JSONObject();

    try {
      object.put("CellPhone", model.getCellPhone());
      object.put("Image", model.getImageUrl());
      object.put("FullName", model.getName());
      object.put("Email", model.getEmail());
    } catch (JSONException e) {
      service.onLoading(false);
      service.onError(Error.getErrorVolley(e.toString()));
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.signup(object);

    disposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onDataSendingState(false);
        service.onResponseReceived(apiDefaultResponse);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onDataSendingState(false);
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  public void requestStoragePermission(Activity activity) {
      Dexter.withContext(activity)
        .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        .withListener(new MultiplePermissionsListener() {
          @Override
          public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

            if (multiplePermissionsReport.areAllPermissionsGranted()) {
              service.onStoragePermissionGranted();
            } else {
              service.onStoragePermissionDenied();
            }
          }

          @Override
          public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

          }
        }).check();
  }

  public void openImageSelector(FragmentActivity activity) {
    Handler handler = new Handler();
    TedBottomPicker.with(activity)
      .setPeekHeight(context.getResources().getDisplayMetrics().heightPixels / 2)
      .showTitle(false)
      .setCompleteButtonText(activity.getString(R.string.submit))
      .setEmptySelectionText(activity.getString(R.string.noPhotoSelected))
      .show(uri -> {
        new Thread(() -> {
          service.onImageUploading(true);
          FileManger fileManger = new FileManger(BaseRepository.API_ADD_IMAGE_USER_PRO);
          CompressImage compressImage = new CompressImage(320, 450, 75, activity);
          String imagePath = uri.getPath();
          String name = GetRandom.GetLong() + ".jpg";
          Bitmap bitmap = compressImage.Compress(imagePath);
          String fileName = fileManger.uploadFile(SaveImageToMob.SaveImageToSdCard(name, bitmap));
          service.onImageUploading(false);
          handler.post(() -> service.onImagesUploaded(fileName, uri));
        }).start();
      });
  }
}
