package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Services.LoginFragmentService;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.LoginRepository;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public class LoginFragmentPresenter {

  Context context;
  LoginFragmentService service;
  LoginRepository repository;
  Disposable disposable;

  public LoginFragmentPresenter(Context context, LoginFragmentService service) {
    this.context = context;
    this.service = service;
    repository = new LoginRepository();
  }

  public void start() {
    service.onPresenterStart();
  }

  public void login(String phoneNumber) {
    service.onLoading(true);
    JSONObject loginObject = new JSONObject();
    try {
      loginObject.put("PhoneNumber", phoneNumber);
    } catch (JSONException e) {
      service.onLoading(false);
      service.onError(ResaultCode.Error);
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.login(loginObject);

    disposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse response) {
        service.onLoading(false);
        service.onResponseReceived(response);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }
}
