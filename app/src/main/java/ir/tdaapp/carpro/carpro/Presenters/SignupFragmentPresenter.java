package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.SignupRepository;
import ir.tdaapp.carpro.carpro.Models.Services.SignupFragmentService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public class SignupFragmentPresenter {

  Context context;
  SignupFragmentService service;
  SignupRepository repository;
  Disposable disposable;

  public SignupFragmentPresenter(Context context, SignupFragmentService service) {
    this.context = context;
    this.service = service;
    repository = new SignupRepository();
  }

  public void start(String cellPhone, String imageUrl, String fullName, String email) {
    service.onPresenterStart();

  }

  private void signup(String cellPhone, String imageUrl, String fullName, String email) {
    service.onLoading(false);
    JSONObject object = new JSONObject();

    try {
      object.put("CellPhone", cellPhone);
      object.put("Image", imageUrl);
      object.put("FullName", fullName);
      object.put("Email", email);
    } catch (JSONException e) {
      service.onLoading(false);
      service.onError(e.getMessage());
      e.printStackTrace();
    }

    Single<ApiDefaultResponse> data = repository.signup(object);

    disposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse apiDefaultResponse) {
        service.onLoading(false);
        service.onResponseReceived(apiDefaultResponse);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }
}
