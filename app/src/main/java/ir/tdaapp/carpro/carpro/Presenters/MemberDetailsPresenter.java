package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Database.Tbl_CarDetails;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.MemberDetailsRepository;
import ir.tdaapp.carpro.carpro.Models.Services.MemberDetailsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;

public class MemberDetailsPresenter {

  Context context;
  MemberDetailsService service;
  MemberDetailsRepository repository;
  Disposable getDetails, getBrands, setBrands, editBrands;
  Tbl_CarDetails carDetails;

  public MemberDetailsPresenter(Context context, MemberDetailsService service) {
    this.context = context;
    this.service = service;
    repository = new MemberDetailsRepository();
    carDetails = new Tbl_CarDetails(context);
  }

  public void start(int id) {
    service.onPresenterStart();
    getDetails(id);
  }

  public void editBrands(int userId, ArrayList<CarDetailsEntryModel> brands) {
    service.onLoading(true);
    JSONObject object = new JSONObject();
    JSONArray array = new JSONArray();
    try {
      object.put("UserId", userId);

      for (CarDetailsEntryModel model : brands)
        array.put(model.getId());

      object.put("Brands", array);
    } catch (JSONException e) {
      service.onLoading(false);
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.editBrands(object);
    editBrands = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        String message = "";
        for (int i = 0; i < apiDefaultResponse.getMessages().size(); i++)
          message = new StringBuilder("").append(apiDefaultResponse
            .getMessages()
            .get(i))
            .append(apiDefaultResponse.getMessages().size() > 1 ? "\n" : "")
            .toString();
        service.onBrandsEdited(message, apiDefaultResponse.isResult());
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  private void getDetails(int id) {
    service.onLoading(true);

    Single<UserModel> data = repository.getUserDetails(id);

    getDetails = data.subscribeWith(new DisposableSingleObserver<UserModel>() {
      @Override
      public void onSuccess(@NonNull UserModel model) {
        service.onDetailsReceived(model);
        getBrands(model.getId());
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  public void getBrandsFromDatabase() {
    service.onBrandsDBReceived(carDetails.getBrands());
  }

  private void getBrands(int id) {
    Single<List<CarDetailsEntryModel>> data = repository.getUserBrands(id);

    getBrands = data.subscribeWith(new DisposableSingleObserver<List<CarDetailsEntryModel>>() {
      @Override
      public void onSuccess(@NonNull List<CarDetailsEntryModel> carDetailsEntryModels) {
        service.onLoading(false);
        setBrands(carDetailsEntryModels);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  private void setBrands(List<CarDetailsEntryModel> carDetailsEntryModels) {
    Observable<CarDetailsEntryModel> observable = Observable.fromIterable(carDetailsEntryModels);

    setBrands = observable.subscribe(carDetailsEntryModel -> {
      service.onBrandReceived(carDetailsEntryModel);
    }, throwable -> {

    }, () -> {
      service.onFinish();
    });
  }
}
