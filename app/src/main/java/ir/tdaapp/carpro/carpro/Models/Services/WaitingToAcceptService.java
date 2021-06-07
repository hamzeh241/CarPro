package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;

public interface WaitingToAcceptService {

  void onPresenterStart();

  void onItemReceived(CarModel item);

  void onError(String message);

  void onLoading(boolean state);

  void onFinish();
}
