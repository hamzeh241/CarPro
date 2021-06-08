package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.CarDetailsRepository;
import ir.tdaapp.carpro.carpro.Models.Services.CarDetailsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;

public class CarDetailsPresenter {

  Context context;
  CarDetailsService service;
  CarDetailsRepository repository;
  Disposable get, confirmDisposable, rejectDisposable;

  public CarDetailsPresenter(Context context, CarDetailsService service) {
    this.context = context;
    this.service = service;
    repository = new CarDetailsRepository();
  }

  public void start(int id) {
    service.onPresenterStart();
    getItem(id);
  }

  private void getItem(int id) {
    service.onLoading(true);
    Single<CarDetailModel> data = repository.getItems(id);

    get = data.subscribeWith(new DisposableSingleObserver<CarDetailModel>() {
      @Override
      public void onSuccess(@NonNull CarDetailModel carDetailModel) {
        service.onLoading(false);
        service.onItemReceived(carDetailModel);
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
        service.onConfirmCarSuccessful(apiDefaultResponse.getMessages().get(0));
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  public void rejectCar(int userId,int carId) {
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
        service.onRejectCarSuccessful(apiDefaultResponse.getMessages().get(0));
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
      object.put("DateInsert", model.getDateInsert());
      object.put("YearOfConstructionId", model.getProductionYearId());
      object.put("ColorId", model.getColorId());
      object.put("Images", model.getPhotos());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    service.onLoading(true);

    Single<ApiDefaultResponse> data = repository.rejectCar(object);

    confirmDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        service.onRejectCarSuccessful(apiDefaultResponse.getMessages().get(0));
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());

      }
    });
  }
}
