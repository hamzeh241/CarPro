package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.RejectedRepository;
import ir.tdaapp.carpro.carpro.Models.Services.RejectedCarsService;
import ir.tdaapp.carpro.carpro.Models.Utilities.Error;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;

public class RejectedCarsPresenter {

  Context context;
  RejectedCarsService service;
  RejectedRepository repository;
  Disposable get, set;

  public RejectedCarsPresenter(Context context, RejectedCarsService service) {
    this.context = context;
    this.service = service;
    repository = new RejectedRepository();
  }

  public void start(int page) {
    service.onPresenterStart();
    getItems(page);
  }

  public void getItemsByPage(int page) {
    getItems(page);
  }

  private void getItems(int page) {
    service.onLoading(true);
    Single<List<CarModel>> data = repository.getItems(page);

    get = data.subscribeWith(new DisposableSingleObserver<List<CarModel>>() {
      @Override
      public void onSuccess(@NonNull List<CarModel> carModels) {
        service.onLoading(false);
        setItems(carModels);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void setItems(List<CarModel> carModels) {
    Observable<CarModel> observable = Observable.fromIterable(carModels);

    set = observable.subscribe(carModel -> {
      service.onItemReceived(carModel);
    }, throwable -> {
    }, () -> {
      service.onFinish();
    });
  }
}
