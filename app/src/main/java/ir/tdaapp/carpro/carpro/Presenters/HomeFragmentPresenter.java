package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.UserEnabledStateRepository;
import ir.tdaapp.carpro.carpro.Models.Services.HomeFragmentService;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;

public class HomeFragmentPresenter {

  Context context;
  HomeFragmentService service;
  UserEnabledStateRepository repository;
  Disposable getDisposable;

  public HomeFragmentPresenter(Context context, HomeFragmentService service) {
    this.context = context;
    this.service = service;
    this.repository = new UserEnabledStateRepository();
  }

  public void start(int id) {
    service.onPresenterStart();
    getUserStatus(id);
  }

  public void getUserStatus(int id) {
    service.onLoading(true);
    Single<ApiDefaultResponse> data = repository.checkStatus(id);

    getDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse model) {
        service.onResponseReceived(model);
        service.onLoading(false);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  public void checkIsAdmin(int id) {
    Single<ApiDefaultResponse> data = repository.checkAdmin(id);

    getDisposable = data.subscribeWith(new DisposableSingleObserver<ApiDefaultResponse>() {
      @Override
      public void onSuccess(@NonNull ApiDefaultResponse model) {
        service.onResponseReceivedAdmin(model);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });

  }
}
