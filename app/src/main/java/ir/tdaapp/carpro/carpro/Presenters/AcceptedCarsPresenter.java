package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.Server.AcceptedRepository;
import ir.tdaapp.carpro.carpro.Models.Services.AcceptedCarsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;

public class AcceptedCarsPresenter {

  Context context;
  AcceptedCarsService service;
  AcceptedRepository repository;
  Disposable get, set;


  public AcceptedCarsPresenter(Context context, AcceptedCarsService service) {
    this.context = context;
    this.service = service;
    repository = new AcceptedRepository();
  }

  public void start(int userId, int page) {
    service.onPresenterStart();
    getItems(userId, page);
  }

  public void getItemsByPage(int userId, int page) {
    getItems(userId, page);
  }

  private void getItems(int userId, int page) {
    service.onLoading(true);
    Single<List<CarModel>> data = repository.getItems(userId, page);

    get = data.subscribeWith(new DisposableSingleObserver<List<CarModel>>() {
      @Override
      public void onSuccess(@NonNull List<CarModel> carModels) {
        service.onLoading(false);
        setItems(carModels);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  private void setItems(List<CarModel> carModels) {
    Observable<CarModel> observable = Observable.fromIterable(carModels);

    set = observable.subscribe(carModel -> {
      service.onItemReceived(carModel);
    }, throwable -> {
      service.onError(throwable.getMessage());
    }, () -> {
      service.onLoading(false);
      service.onFinish();
    });
  }
}
