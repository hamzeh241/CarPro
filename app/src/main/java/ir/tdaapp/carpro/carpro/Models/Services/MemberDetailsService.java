package ir.tdaapp.carpro.carpro.Models.Services;

import java.util.List;

import ir.tdaapp.carpro.carpro.Models.ViewModels.ApiDefaultResponse;
import ir.tdaapp.carpro.carpro.Models.ViewModels.CarDetailsEntryModel;
import ir.tdaapp.carpro.carpro.Models.ViewModels.UserModel;
import ir.tdaapp.li_volley.Enum.ResaultCode;

public interface MemberDetailsService {

  void onLoading(boolean state);

  void onDataSendingLoading(boolean state);

  void onPresenterStart();

  void onError(ResaultCode code);

  void onFinish();

  void onDetailsReceived(UserModel model);

  void onBrandReceived(CarDetailsEntryModel model);

  void onBrandsDBReceived(List<CarDetailsEntryModel> models);

  void onBrandsEdited(String message, boolean result);
}
