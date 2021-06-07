package ir.tdaapp.carpro.carpro.Presenters;

import android.content.Context;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.carpro.carpro.Models.Repository.CarDetailsRepository;
import ir.tdaapp.carpro.carpro.Models.Services.CarDetailsService;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;

public class CarDetailsPresenter {

  Context context;
  CarDetailsService service;
  CarDetailsRepository repository;
  Disposable get;

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
}
