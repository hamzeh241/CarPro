package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.AuthorizeRepository;
import ir.tdaapp.carpro.carpro.Models.Services.AuthorizeDialogService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public class AuthorizeDialogPresenter {

  Context context;
  AuthorizeDialogService service;
  AuthorizeRepository repository;
  Disposable disposable;

  public AuthorizeDialogPresenter(Context context, AuthorizeDialogService service) {
    this.context = context;
    this.service = service;
    repository = new AuthorizeRepository();
  }

  public void start(String phoneNumber, String password) {
    service.onPresenterStart();
  }

  public void authorize(String phoneNumber, String password) {
    service.onLoading(true);
    JSONObject object = new JSONObject();
    try {
      object.put("PhoneNumber", phoneNumber);
      object.put("Password", password);
    } catch (JSONException e) {
      service.onLoading(false);
      service.onError(e.getMessage());
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.authorize(object);
    disposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        service.onDataReceived(apiDefaultResponse);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }
}
