package ir.tdaapp.carpro.carpro.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import es.dmoral.toasty.Toasty;
import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_CarDetails;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.BaseRepository;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.CarDetailsRepository;
import ir.tdaapp.carpro.carpro.Models.Services.CarDetailsService;
import ir.tdaapp.carpro.carpro.Models.Utilities.FileManger;
import ir.tdaapp.carpro.carpro.Models.Utilities.GetRandom;
import ir.tdaapp.carpro.carpro.Models.Utilities.SaveImageToMob;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarChipsListModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailPhotoModel;
import ir.tdaapp.carpro.carpro.R;
import ir.tdaapp.li_image.ImagesCodes.CompressImage;

public class CarDetailsPresenter {

  Context context;
  CarDetailsService service;
  CarDetailsRepository repository;
  Disposable get, confirmDisposable, rejectDisposable, getCategories;
  Tbl_CarDetails carDetails;

  int modelId;

  public CarDetailsPresenter(Context context, CarDetailsService service) {
    this.context = context;
    this.service = service;
    repository = new CarDetailsRepository();
    carDetails = new Tbl_CarDetails(context);
  }

  public void start(int id) {
    service.onPresenterStart();
    getItem(id);
  }

  public void requestStoragePermission(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      Dexter.withActivity(activity)
        .withPermissions(Manifest.permission_group.STORAGE, Manifest.permission.CAMERA)
        .withListener(new MultiplePermissionsListener() {
          @Override
          public void onPermissionsChecked(MultiplePermissionsReport report) {
            if (report.areAllPermissionsGranted()) {
              service.onStoragePermissionGranted();
            } else {
              service.onStoragePermissionDenied();
            }
          }

          @Override
          public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

          }
        }).check();
    } else service.onStoragePermissionGranted();
  }

  public void openImageSelector(FragmentActivity activity) {
    Handler handler = new Handler();
    List<Uri> selectedUriList = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    TedBottomPicker.with(activity)
      .setPeekHeight(activity.getResources().getDisplayMetrics().heightPixels / 2)
      .showTitle(false)
      .setSelectMaxCount(6)
      .setSelectMaxCountErrorText(activity.getString(R.string.maximumPhotoSelectionReached6))
      .setCompleteButtonText(activity.getString(R.string.submit))
      .setEmptySelectionText(activity.getString(R.string.noPhotoSelected))
      .setSelectedUriList(selectedUriList)
      .showMultiImage(uriList -> {
        new Thread(() -> {
          service.onImageUploading(true);

          FileManger fileManger = new FileManger(BaseRepository.API_IMAGE_UPLOAD);
          CompressImage compressImage = new CompressImage(320, 450, 75, activity);
          for (Uri uri : uriList) {
            String imagePath = uri.getPath();
            Bitmap b = compressImage.Compress(imagePath);
            String Name = GetRandom.GetLong() + ".jpg";
            images.add(fileManger.uploadFile(SaveImageToMob.SaveImageToSdCard(Name, b)));
          }
          service.onImageUploading(false);
          handler.post(() -> service.onImagesUploaded(images, uriList));
        }).start();
      });
  }

  private void getFromDatabase() {
    service.onBrandsReceived(carDetails.getBrands(), getParentId(modelId));
    service.onBodyStatusReceived(carDetails.getBodyConditions());
    service.onChassisStatusReceived(carDetails.getChassisStatus());
    service.onProductionYearsReceived(carDetails.getConstructionYears());
    service.onDocumentReceived(carDetails.getDocuments());
    service.onEngineStatusReceived(carDetails.getEngineStatus());
    service.onGearBoxReceived(carDetails.getGearboxes());
    service.onInsuranceDeadlineReceived(carDetails.getInsuranceDeadlines());
    service.onSellTypeReceived(carDetails.getSellType());
    service.onColorsReceived(carDetails.getColors());
  }

  public void getBrands() {
    service.onBrandsReceived(carDetails.getBrands(), modelId);
  }

  public void getModels(int brandId) {
    service.onModelsReceived(carDetails.getModels(brandId));
  }

  public int getParentId(int modelId) {
    return carDetails.getBrandByModel(modelId);
  }

  private void getCategories() {
    Single<List<CarChipsListModel>> data = repository.getCategories();

    getCategories = data.subscribeWith(new DisposableSingleObserver<List<CarChipsListModel>>() {
      @Override
      public void onSuccess(List<CarChipsListModel> categoryModels) {
        service.onCategoriesReceived(categoryModels);
        service.onLoading(false);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void getItem(int id) {
    service.onLoading(true);
    Single<CarDetailModel> data = repository.getItems(id);

    get = data.subscribeWith(new DisposableSingleObserver<CarDetailModel>() {
      @Override
      public void onSuccess(@NonNull CarDetailModel carDetailModel) {
        modelId = carDetailModel.getBrandId();
        service.onLoading(false);
        service.onItemReceived(carDetailModel);
        getCategories();
        getFromDatabase();
      }

      @Override
      public void onError(@NonNull Throwable e) {

      }
    });
  }

  public void confirmCar(int userId, int carId) {
    JSONObject object = new JSONObject();
    try {
      object.put("UserId", userId);
      object.put("CarId", carId);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    service.onLoading(true);

    Single<ApiDefaultResponse> data = repository.confirmCar(object);

    confirmDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        String message = "";
        for (int i = 0; i < apiDefaultResponse.getMessages().size(); i++) {
          message = new StringBuilder().append(apiDefaultResponse.getMessages().get(i)).append("\n").toString();
        }
        service.onConfirmCarSuccessful(apiDefaultResponse.isResult(), message);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  public void rejectCar(int userId, int carId) {
    JSONObject object = new JSONObject();
    try {
      object.put("UserId", userId);
      object.put("CarId", carId);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    service.onLoading(true);

    Single<ApiDefaultResponse> data = repository.rejectCar(object);

    confirmDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        String message = "";
        for (int i = 0; i < apiDefaultResponse.getMessages().size(); i++) {
          message = new StringBuilder().append(apiDefaultResponse.getMessages().get(i)).append("\n").toString();
        }
        service.onRejectCarSuccessful(apiDefaultResponse.isResult(), message);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  public void editCar(CarDetailModel model) {
    JSONObject object = new JSONObject();
    try {
      object.put("Id", model.getId());
      object.put("Function", model.getFunction());
      object.put("Price", model.getPrice());
      object.put("BrandId", model.getBrandId());
      object.put("EngineStatusId", model.getEngineStatusId());
      object.put("ChassisConditionId", model.getChassisStatusId());
      object.put("BodyConditionId", model.getCarBodyStatusId());
      object.put("InsuranceDeadlineId", model.getInsuranceTimeId());
      object.put("GearboxId", model.getGearBoxId());
      object.put("DocumentId", model.getDocumentId());
      object.put("CategoryId", model.getCategoryId());
      object.put("HowToSellIdId", model.getHowToSellId());
      object.put("Exchange", model.isExchange());
      object.put("Title", model.getTitle());
      object.put("Description", model.getDescription());
      object.put("Phone", model.getPhone());
      object.put("Address", model.getAddress());
      object.put("DateInsert", "");
      object.put("YearOfConstructionId", model.getProductionYearId());
      object.put("ColorId", model.getColorId());

      JSONArray array = new JSONArray();
      for (CarDetailPhotoModel model1 : model.getPhotos())
        array.put(model1.getImageName());

      object.put("Images", array);

    } catch (JSONException e) {
      e.printStackTrace();
    }

    service.onLoading(true);

    Single<ApiDefaultResponse> data = repository.editCar(object);

    confirmDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        String message = "";
        for (int i = 0; i < apiDefaultResponse.getMessages().size(); i++) {
          message = new StringBuilder().append(apiDefaultResponse.getMessages().get(i)).append("\n").toString();
        }
        service.onEditCarSuccessful(apiDefaultResponse.isResult(), message);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
        Log.i("TAG", "onError: ");
      }
    });
  }
}
