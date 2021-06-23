package ir.tdaapp.carpro.carpro.Models.Services;

import ir.tdaapp.carpro.carpro.Models.ViewModels.CarModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface AcceptedCarsService {

  void onPresenterStart();

  void onItemReceived(CarModel item);

  void onError(ResaultCode code);

  void onLoading(boolean state);

  void onFinish();
}
