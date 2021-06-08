package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;

public interface CarDetailsService {

  void onPresenterStart();

  void onItemReceived(CarDetailModel item);

  void onConfirmCarSuccessful(String message);

  void onRejectCarSuccessful(String message);

  void onError(String message);

  void onLoading(boolean state);
}